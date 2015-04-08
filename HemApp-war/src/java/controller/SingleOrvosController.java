/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Beteg;
import entity.Injekciotortenet;
import entity.Keszitmeny;
import entity.Orvos;
import entity.Uzenet;
import facade.BetegFacade;
import facade.InjekciotortenetFacade;
import facade.KeszitmenyFacade;
import facade.OrvosFacade;
import facade.UzenetFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author gczuczor
 */
@ManagedBean(name = "SingleOrvosController")
@SessionScoped
@Stateless
public class SingleOrvosController implements Serializable {

    @ManagedProperty("#{LoginController}")
    private LoginController loginController;

    @EJB
    private BetegFacade betegFacade;
    @EJB
    private OrvosFacade orvosFacade;
    @EJB
    private UzenetFacade uzenetFacade;
    @EJB
    private KeszitmenyFacade keszitmenyFacade;
    @EJB
    private InjekciotortenetFacade injekciotortenetFacade;

    private List<Orvos> allOrvos;
    private List<Uzenet> myUzenetListByBeteg;
    private List<Uzenet> segedUzenetList;
    private List<Beteg> betegekForOrvos;
    private List<Injekciotortenet> selectedInjekcioTortenetList;
    private List<Keszitmeny> allKeszitmeny;

    private Orvos actOrvos;
    private Beteg selectedBeteg;
    private Keszitmeny selectedKeszitmeny;

    private String selectedNE;
    private String selectedKeszitmenyID;
    private String selectedBetegID;
    private String felhNev = "";
    private String felhNevForChange = "";
    private String jelszo = "";
    private String oldPassword = "";
    private String newPassword = "";
    private String vezeteknev;
    private String keresztnev;
    private String email;
    private String telefon;
    private String emailForChange;
    private String telefonForChange;
    private String taj;
    private String szoveg;

    private String kepLink;
    private boolean badFileFormat = false;
    private int uzenetStartIndex = 0;

    @PostConstruct
    public void init() {
        initAllOrvos();
        actOrvos = loginController.getOrvos();
        felhNevForChange = actOrvos.getFelhnev();
        emailForChange = actOrvos.getEmail();
        telefonForChange = actOrvos.getTelefon();
        betegekForOrvos = new LinkedList<>(actOrvos.getBetegCollection());
        if (!betegekForOrvos.isEmpty()) {
            selectedBetegID = betegekForOrvos.get(0).getId();
            selectedBeteg = betegekForOrvos.get(0);
            selectedInjekcioTortenetList = new LinkedList<>(selectedBeteg.getInjekciotortenetCollection());
            Collections.sort(selectedInjekcioTortenetList, new Comparator<Injekciotortenet>() {

                @Override
                public int compare(Injekciotortenet o1, Injekciotortenet o2) {
                    if (o1.getKezdetdatum().getTime() == o2.getKezdetdatum().getTime()) {
                        return 0;
                    } else {
                        return o2.getKezdetdatum().getTime() - o1.getKezdetdatum().getTime() > 0 ? -1 : 1;
                    }
                }

            });
        }
        allKeszitmeny = keszitmenyFacade.findAll();

        if (!allKeszitmeny.isEmpty()) {
            selectedKeszitmeny = allKeszitmeny.get(0);
        }
        initUzenetByBeteg();
    }

    public void initAllOrvos() {
        allOrvos = orvosFacade.findAll();
    }

    public void initUzenetByBeteg() {
        if (selectedBetegID != null && !betegFacade.getByID(selectedBetegID).isEmpty()) {
            selectedBeteg = betegFacade.getByID(selectedBetegID).get(0);
            segedUzenetList = uzenetFacade.getByBetegAndOrvos(selectedBeteg, actOrvos);
            if (segedUzenetList.size() > uzenetStartIndex + 5) {
                myUzenetListByBeteg = segedUzenetList.subList(uzenetStartIndex, uzenetStartIndex + 5);
            } else {
                myUzenetListByBeteg = segedUzenetList.subList(uzenetStartIndex, segedUzenetList.size());
            }
        }
    }

    public void decreaseStartIndex() {
        if (uzenetStartIndex > 0) {
            uzenetStartIndex--;
            initUzenetByBeteg();
            RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:uzenetSzamLabel");
            RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:uzenetSzamLabel2");
            for (int i = 0; i < 5; i++) {
                RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:" + i + ":uzenetOutputPanel");
            }
        }
    }

    public void increaseStartIndex() {
        if (uzenetStartIndex < segedUzenetList.size() - 5) {
            uzenetStartIndex++;
            initUzenetByBeteg();
            RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:uzenetSzamLabel");
            RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:uzenetSzamLabel2");
            for (int i = 0; i < 5; i++) {
                RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:" + i + ":uzenetOutputPanel");
            }
        }
    }

    public void updateSelectedBeteg() {
        selectedBeteg = betegFacade.getByID(selectedBetegID).get(0);
        selectedInjekcioTortenetList = new LinkedList<>(selectedBeteg.getInjekciotortenetCollection());
        Collections.sort(selectedInjekcioTortenetList, new Comparator<Injekciotortenet>() {

            @Override
            public int compare(Injekciotortenet o1, Injekciotortenet o2) {
                if (o1.getKezdetdatum().getTime() == o2.getKezdetdatum().getTime()) {
                    return 0;
                } else {
                    return o1.getKezdetdatum().getTime() - o2.getKezdetdatum().getTime() > 0 ? -1 : 1;
                }
            }

        });
    }

    public void updateSelectedKeszitmeny() {
        selectedKeszitmeny = keszitmenyFacade.getByID(selectedKeszitmenyID).get(0);

    }

    public void createInjekcioTortenet() {
        try {
            Integer.parseInt(selectedNE);
        } catch (NumberFormatException e) {
            RequestContext.getCurrentInstance().execute("PF('editInjekciotortenetDialogWidget').hide();");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Csak számokat adjon meg!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        List<Injekciotortenet> seged = injekciotortenetFacade.getActualByBeteg(selectedBeteg);

        Date actDate = new Date();

        Injekciotortenet injekciotortenet = new Injekciotortenet(actDate, Integer.parseInt(selectedNE));
        injekciotortenet.setBetegID(selectedBeteg);
        injekciotortenet.setKeszitmenyID(selectedKeszitmeny);
        if (seged != null && !seged.isEmpty()) {
            seged.get(0).setVegedatum(actDate);
            injekciotortenetFacade.edit(seged.get(0));
        }
        injekciotortenetFacade.create(injekciotortenet);
        selectedBeteg = betegFacade.getByID(selectedBetegID).get(0);
        selectedInjekcioTortenetList = new LinkedList<>(selectedBeteg.getInjekciotortenetCollection());
        Collections.sort(selectedInjekcioTortenetList, new Comparator<Injekciotortenet>() {

            @Override
            public int compare(Injekciotortenet o1, Injekciotortenet o2) {
                if (o1.getKezdetdatum().getTime() == o2.getKezdetdatum().getTime()) {
                    return 0;
                } else {
                    return o1.getKezdetdatum().getTime() - o2.getKezdetdatum().getTime() > 0 ? -1 : 1;
                }
            }

        });
        selectedNE = null;
        RequestContext.getCurrentInstance().execute("PF('editInjekciotortenetDialogWidget').hide();");
    }

    public void refreshPage() {
        initUzenetByBeteg();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./messages.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SingleBetegController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StreamedContent fileDownload(String src) {
        InputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(src);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SingleBetegController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultStreamedContent dsc = new DefaultStreamedContent(fileInputStream);
        dsc.setName(src.split("/")[src.split("/").length - 1]);
        return dsc;
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile kep = (UploadedFile) event.getFile();

        InputStream inputStr = null;

        if (kep.getSize() == 0) {
            return;
        }

        try {
            inputStr = kep.getInputstream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        kepLink = (System.getProperty("user.home") + "/Desktop/hemAppKepek/" + new Date(System.currentTimeMillis()).getTime() + "/" + kep.getFileName()).replaceAll("\\\\", "/");
        File destFile = new File(kepLink);

        String mimetype = new MimetypesFileTypeMap().getContentType(destFile);
        String type = mimetype.split("/")[0];
        if (!type.equals("image")) {
            badFileFormat = true;
            RequestContext.getCurrentInstance().execute("PF('uzenetDialogWidget').hide()");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A kép formátuma nem megfelelő!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        try {
            FileUtils.copyInputStreamToFile(inputStr, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (inputStr != null) {
                inputStr.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage() {
        if (szoveg == null || szoveg.length() == 0) {
            RequestContext.getCurrentInstance().execute("PF('uzenetDialogWidget').hide()");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Az üzenet nem lehet üres!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        if (badFileFormat) {
            badFileFormat = false;
            return;
        }
        uzenetFacade.create(new Uzenet(new Date(System.currentTimeMillis()), szoveg, kepLink, selectedBeteg, actOrvos));
        szoveg = kepLink = null;
        initUzenetByBeteg();
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/orvos/orvoslogin?faces-redirect=true";
    }

    public void editJelszo() {
        if (!actOrvos.getJelszo().equals(loginController.getMD5String(oldPassword))) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A régi jelszó nem megfelelő!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        actOrvos.setJelszo(loginController.getMD5String(newPassword));

        orvosFacade.edit(actOrvos);
        loginController.setOrvos(actOrvos);
        loginController.setJelszo(newPassword);
        oldPassword = newPassword = "";
        RequestContext.getCurrentInstance().update("editJelszoForm");
        RequestContext.getCurrentInstance().reset("page:editJelszoForm:");
        RequestContext.getCurrentInstance().execute("PF('editJelszoDialogWidget').hide();");
    }

    public void editOrvos() {
        for (Orvos orvos : allOrvos) {
            if (orvos.getFelhnev().equals(felhNevForChange) && !actOrvos.getFelhnev().equals(felhNevForChange)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A felhasználónév foglalt!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }

        actOrvos.setFelhnev(felhNevForChange);
        actOrvos.setEmail(emailForChange);
        actOrvos.setTelefon(telefonForChange);

        if (emailForChange.isEmpty()) {
            actOrvos.setEmail(null);
        }

        if (telefonForChange.isEmpty()) {
            actOrvos.setTelefon(null);
        }

        orvosFacade.edit(actOrvos);
        loginController.setOrvos(actOrvos);
        loginController.setFelhNev(actOrvos.getFelhnev());
        initAllOrvos();
        RequestContext.getCurrentInstance().update("tableForm");
        RequestContext.getCurrentInstance().update("editBetegForm");
        RequestContext.getCurrentInstance().execute("PF('editBetegDialogWidget').hide();");
    }

    public Orvos getActOrvos() {
        return actOrvos;
    }

    public void setActOrvos(Orvos actOrvos) {
        this.actOrvos = actOrvos;
    }

    public String getTaj() {
        return taj;
    }

    public void setTaj(String taj) {
        this.taj = taj;
    }

    public String getFelhNev() {
        return felhNev;
    }

    public void setFelhNev(String felhNev) {
        this.felhNev = felhNev;
    }

    public String getFelhNevForChange() {
        return felhNevForChange;
    }

    public void setFelhNevForChange(String felhNevForChange) {
        this.felhNevForChange = felhNevForChange;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVezeteknev() {
        return vezeteknev;
    }

    public void setVezeteknev(String vezeteknev) {
        this.vezeteknev = vezeteknev;
    }

    public String getKeresztnev() {
        return keresztnev;
    }

    public void setKeresztnev(String keresztnev) {
        this.keresztnev = keresztnev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmailForChange() {
        return emailForChange;
    }

    public void setEmailForChange(String emailForChange) {
        this.emailForChange = emailForChange;
    }

    public String getTelefonForChange() {
        return telefonForChange;
    }

    public void setTelefonForChange(String telefonForChange) {
        this.telefonForChange = telefonForChange;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public List<Uzenet> getMyUzenetListByBeteg() {
        return myUzenetListByBeteg;
    }

    public void setMyUzenetListByBeteg(List<Uzenet> myUzenetListByBeteg) {
        this.myUzenetListByBeteg = myUzenetListByBeteg;
    }

    public String getSelectedBetegID() {
        return selectedBetegID;
    }

    public void setSelectedBetegID(String selectedBetegID) {
        this.selectedBetegID = selectedBetegID;
    }

    public String getSzoveg() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg = szoveg;
    }

    public int getUzenetStartIndex() {
        return uzenetStartIndex;
    }

    public void setUzenetStartIndex(int uzenetStartIndex) {
        this.uzenetStartIndex = uzenetStartIndex;
    }

    public List<Uzenet> getSegedUzenetList() {
        return segedUzenetList;
    }

    public void setSegedUzenetList(List<Uzenet> segedUzenetList) {
        this.segedUzenetList = segedUzenetList;
    }

    public List<Beteg> getBetegekForOrvos() {
        return betegekForOrvos;
    }

    public void setBetegekForOrvos(List<Beteg> betegekForOrvos) {
        this.betegekForOrvos = betegekForOrvos;
    }

    public Beteg getSelectedBeteg() {
        return selectedBeteg;
    }

    public void setSelectedBeteg(Beteg selectedBeteg) {
        this.selectedBeteg = selectedBeteg;
    }

    public String getSelectedKeszitmenyID() {
        return selectedKeszitmenyID;
    }

    public void setSelectedKeszitmenyID(String selectedKeszitmenyID) {
        this.selectedKeszitmenyID = selectedKeszitmenyID;
    }

    public Keszitmeny getSelectedKeszitmeny() {
        return selectedKeszitmeny;
    }

    public void setSelectedKeszitmeny(Keszitmeny selectedKeszitmeny) {
        this.selectedKeszitmeny = selectedKeszitmeny;
    }

    public List<Keszitmeny> getAllKeszitmeny() {
        return allKeszitmeny;
    }

    public void setAllKeszitmeny(List<Keszitmeny> allKeszitmeny) {
        this.allKeszitmeny = allKeszitmeny;
    }

    public String getSelectedNE() {
        return selectedNE;
    }

    public void setSelectedNE(String selectedNE) {
        this.selectedNE = selectedNE;
    }

    public List<Injekciotortenet> getSelectedInjekcioTortenetList() {
        return selectedInjekcioTortenetList;
    }

    public void setSelectedInjekcioTortenetList(List<Injekciotortenet> selectedInjekcioTortenetList) {
        this.selectedInjekcioTortenetList = selectedInjekcioTortenetList;
    }

}
