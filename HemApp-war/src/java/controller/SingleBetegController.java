/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Beteg;
import entity.Injekciotortenet;
import entity.Orvos;
import entity.Uzenet;
import facade.BetegFacade;
import facade.OrvosFacade;
import facade.UzenetFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
@ManagedBean(name = "SingleBetegController")
@SessionScoped
@Stateless
public class SingleBetegController implements Serializable {

    @ManagedProperty("#{LoginController}")
    private LoginController loginController;

    @EJB
    private BetegFacade betegFacade;
    @EJB
    private OrvosFacade orvosFacade;
    @EJB
    private UzenetFacade uzenetFacade;

    private List<Beteg> allBeteg;
    private List<Uzenet> myUzenetListByOrvos;
    private List<Uzenet> segedUzenetList;
    private List<Injekciotortenet> injekciotortenetList;
    private HashSet<Orvos> orvosok = new HashSet<>();

    private Beteg actBeteg;
    private Orvos kezeloOrvos;
    private Orvos selectedOrvos;

    private String selectedOrvosID;
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
        init2();
        actBeteg = loginController.getBeteg();
        felhNevForChange = actBeteg.getFelhnev();
        emailForChange = actBeteg.getEmail();
        telefonForChange = actBeteg.getTelefon();
        kezeloOrvos = actBeteg.getOrvosID();
        orvosok.add(kezeloOrvos);
        selectedOrvosID = kezeloOrvos.getId();
        selectedOrvos = kezeloOrvos;
        initUzenetByOrvos();
        orvosok.addAll(uzenetFacade.getOrvosListByBeteg(actBeteg));
        injekciotortenetList = new LinkedList<>(actBeteg.getInjekciotortenetCollection());
        Collections.sort(injekciotortenetList, new Comparator<Injekciotortenet>() {

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

    public void init2() {
        allBeteg = betegFacade.findAll();
    }

    public void decreaseStartIndex() {
        if (uzenetStartIndex > 0) {
            uzenetStartIndex--;
            initUzenetByOrvos();
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
            initUzenetByOrvos();
            RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:uzenetSzamLabel");
            RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:uzenetSzamLabel2");
            for (int i = 0; i < 5; i++) {
                RequestContext.getCurrentInstance().update("page:uzenetek:uzenetDataList:" + i + ":uzenetOutputPanel");
            }
        }
    }

    public void initUzenetByOrvos() {
        selectedOrvos = orvosFacade.getByID(selectedOrvosID).get(0);
        segedUzenetList = uzenetFacade.getByBetegAndOrvos(actBeteg, selectedOrvos);
        if (segedUzenetList.size() > uzenetStartIndex + 5) {
            myUzenetListByOrvos = segedUzenetList.subList(uzenetStartIndex, uzenetStartIndex + 5);
        } else {
            myUzenetListByOrvos = segedUzenetList.subList(uzenetStartIndex, segedUzenetList.size());
        }
    }
    
    public void refreshPage() {
        initUzenetByOrvos();
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
        if(szoveg == null || szoveg.length() == 0) {
            RequestContext.getCurrentInstance().execute("PF('uzenetDialogWidget').hide()");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Az üzenet nem lehet üres!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        if (badFileFormat) {
            badFileFormat = false;
            return;
        }
        uzenetFacade.create(new Uzenet(new Date(System.currentTimeMillis()), szoveg, kepLink, actBeteg, kezeloOrvos));
        szoveg = kepLink = null;
        initUzenetByOrvos();
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/beteg/beteglogin?faces-redirect=true";
    }

    public void editJelszo() {
        if (!actBeteg.getJelszo().equals(loginController.getMD5String(oldPassword))) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A régi jelszó nem megfelelő!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        actBeteg.setJelszo(loginController.getMD5String(newPassword));

        betegFacade.edit(actBeteg);
        loginController.setBeteg(actBeteg);
        loginController.setJelszo(newPassword);
        oldPassword = newPassword = "";
        RequestContext.getCurrentInstance().update("editJelszoForm");
        RequestContext.getCurrentInstance().reset("page:editJelszoForm:");
        RequestContext.getCurrentInstance().execute("PF('editJelszoDialogWidget').hide()");
    }

    public void editBeteg() {
        for (Beteg beteg : allBeteg) {
            if (beteg.getFelhnev().equals(felhNevForChange) && !actBeteg.getFelhnev().equals(felhNevForChange)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A felhasználónév foglalt!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }

        actBeteg.setFelhnev(felhNevForChange);
        actBeteg.setEmail(emailForChange);
        actBeteg.setTelefon(telefonForChange);

        if (emailForChange.isEmpty()) {
            actBeteg.setEmail(null);
        }

        if (telefonForChange.isEmpty()) {
            actBeteg.setTelefon(null);
        }

        betegFacade.edit(actBeteg);
        loginController.setBeteg(actBeteg);
        loginController.setFelhNev(actBeteg.getFelhnev());
        init2();
        RequestContext.getCurrentInstance().update("tableForm");
        RequestContext.getCurrentInstance().update("editBetegForm");
        RequestContext.getCurrentInstance().execute("PF('editBetegDialogWidget').hide()");
    }

    public Beteg getActBeteg() {
        return actBeteg;
    }

    public void setActBeteg(Beteg actBeteg) {
        this.actBeteg = actBeteg;
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

    public Orvos getKezeloOrvos() {
        return kezeloOrvos;
    }

    public void setKezeloOrvos(Orvos kezeloOrvos) {
        this.kezeloOrvos = kezeloOrvos;
    }

    public List<Uzenet> getMyUzenetListByOrvos() {
        return myUzenetListByOrvos;
    }

    public void setMyUzenetListByOrvos(List<Uzenet> myUzenetListByOrvos) {
        this.myUzenetListByOrvos = myUzenetListByOrvos;
    }

    public HashSet<Orvos> getOrvosok() {
        return orvosok;
    }

    public void setOrvosok(HashSet<Orvos> orvosok) {
        this.orvosok = orvosok;
    }

    public String getSelectedOrvosID() {
        return selectedOrvosID;
    }

    public void setSelectedOrvosID(String selectedOrvosID) {
        this.selectedOrvosID = selectedOrvosID;
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

    public List<Injekciotortenet> getInjekciotortenetList() {
        return injekciotortenetList;
    }

    public void setInjekciotortenetList(List<Injekciotortenet> injekciotortenetList) {
        this.injekciotortenetList = injekciotortenetList;
    }

}
