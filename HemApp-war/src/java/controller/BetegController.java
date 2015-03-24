/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Beteg;
import entity.Orvos;
import facade.BetegFacade;
import facade.OrvosFacade;
import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author gczuczor
 */
@ManagedBean(name = "BetegController")
@SessionScoped
@Stateless
public class BetegController implements Serializable {

    @EJB
    private LoginController loginController;

    @EJB
    private BetegFacade betegFacade;
    @EJB
    private OrvosFacade orvosFacade;

    private List<Beteg> allBeteg;
    private List<Beteg> filteredBeteg;
    private List<Orvos> allOrvos;
    private List<Orvos> filteredOrvos;

    private Beteg selectedBeteg;
    private Orvos selectedOrvos;
    private Orvos selectedOrvosForChange;

    private String felhNev = "";
    private String felhNevForChange = "";
    private String felhNevForChangePW = "";
    private String jelszo = "";
    private String oldPassword = "";
    private String newPassword = "";
    private String vezeteknev;
    private String keresztnev;
    private String email;
    private String telefon;
    private String vezeteknevForChange;
    private String keresztnevForChange;
    private String emailForChange;
    private String telefonForChange;
    private String taj;
    private String tajForChange;

    @PostConstruct
    public void init() {
        allBeteg = betegFacade.findAll();
        if (allOrvos == null) {
            allOrvos = orvosFacade.findAll();
            allOrvos.sort(new Comparator<Orvos>() {

                @Override
                public int compare(Orvos o1, Orvos o2) {
                    return (o1.getVezeteknev() + o1.getKeresztnev()).compareTo(o2.getVezeteknev() + o2.getKeresztnev());
                }
            });
        }
    }

    public List<Orvos> complete(String query) {
        List<Orvos> eredmeny = new LinkedList<>();
        query = query.toLowerCase();
        for (int i = 0; i < allOrvos.size(); i++) {
            String tmp = allOrvos.get(i).getVezeteknev().toLowerCase() + " " + allOrvos.get(i).getKeresztnev().toLowerCase();
            if (tmp.contains(query)) {
                eredmeny.add(allOrvos.get(i));
            }
        }
        return eredmeny;
    }

    public void selectBeteg(SelectEvent selectEvent) {
        selectedBeteg = (Beteg) selectEvent.getObject();
        felhNevForChange = selectedBeteg.getFelhnev();
        vezeteknevForChange = selectedBeteg.getVezeteknev();
        keresztnevForChange = selectedBeteg.getKeresztnev();
        emailForChange = selectedBeteg.getEmail();
        telefonForChange = selectedBeteg.getTelefon();
        tajForChange = String.valueOf(selectedBeteg.getTaj());
        selectedOrvosForChange = selectedBeteg.getOrvosID();
        RequestContext.getCurrentInstance().update("editBetegForm");
    }

    public void createBeteg() {
        for (Beteg beteg : allBeteg) {
            if (beteg.getFelhnev().equals(felhNev)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A felhasználónév foglalt!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        Beteg newBeteg;
        if (!jelszo.equals("")) {
            newBeteg = new Beteg(felhNev, loginController.getMD5String(jelszo), vezeteknev, keresztnev, Integer.parseInt(taj));
        } else {
            newBeteg = new Beteg(felhNev, loginController.getMD5String("start123"), vezeteknev, keresztnev, Integer.parseInt(taj));
        }

        if (!email.isEmpty()) {
            newBeteg.setEmail(email);
        }
        if (!telefon.isEmpty()) {
            newBeteg.setTelefon(telefon);
        }

        newBeteg.setOrvosID(selectedOrvos);

        betegFacade.create(newBeteg);
        init();
        felhNev = jelszo = vezeteknev = keresztnev = taj = "";
        email = telefon = null;
        RequestContext.getCurrentInstance().update("tableForm");
        RequestContext.getCurrentInstance().update("newBetegForm");
    }

    public void editBetegShow() {
        if (selectedBeteg == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon beteget!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        RequestContext.getCurrentInstance().execute("PF('editBetegDialogWidget').show()");
    }

    public void editBeteg() {
        for (Beteg beteg : allBeteg) {
            if (beteg.getFelhnev().equals(felhNevForChange) && !selectedBeteg.getFelhnev().equals(felhNevForChange)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A felhasználónév foglalt!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        if (selectedBeteg == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon beteget!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        selectedBeteg.setFelhnev(felhNevForChange);
        selectedBeteg.setVezeteknev(vezeteknevForChange);
        selectedBeteg.setKeresztnev(keresztnevForChange);
        selectedBeteg.setEmail(emailForChange);
        selectedBeteg.setTelefon(telefonForChange);
        selectedBeteg.setTaj(Integer.parseInt(tajForChange));

        if (emailForChange.isEmpty()) {
            selectedBeteg.setEmail(null);
        }

        if (telefonForChange.isEmpty()) {
            selectedBeteg.setTelefon(null);
        }

        selectedBeteg.setOrvosID(selectedOrvosForChange);

        betegFacade.edit(selectedBeteg);
        init();
        RequestContext.getCurrentInstance().update("tableForm");
        RequestContext.getCurrentInstance().update("editBetegForm");
    }

    public void deleteBeteg() {
        if (selectedBeteg == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon beteget!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        betegFacade.remove(selectedBeteg);
        init();
        RequestContext.getCurrentInstance().update("tableForm");
    }

    public List<Beteg> getAllBeteg() {
        return allBeteg;
    }

    public void setAllBeteg(List<Beteg> allBeteg) {
        this.allBeteg = allBeteg;
    }

    public List<Beteg> getFilteredBeteg() {
        return filteredBeteg;
    }

    public void setFilteredBeteg(List<Beteg> filteredBeteg) {
        this.filteredBeteg = filteredBeteg;
    }

    public Beteg getSelectedBeteg() {
        return selectedBeteg;
    }

    public void setSelectedBeteg(Beteg selectedBeteg) {
        this.selectedBeteg = selectedBeteg;
    }

    public String getTaj() {
        return taj;
    }

    public void setTaj(String taj) {
        this.taj = taj;
    }

    public String getTajForChange() {
        return tajForChange;
    }

    public void setTajForChange(String tajForChange) {
        this.tajForChange = tajForChange;
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

    public String getFelhNevForChangePW() {
        return felhNevForChangePW;
    }

    public void setFelhNevForChangePW(String felhNevForChangePW) {
        this.felhNevForChangePW = felhNevForChangePW;
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

    public String getVezeteknevForChange() {
        return vezeteknevForChange;
    }

    public void setVezeteknevForChange(String vezeteknevForChange) {
        this.vezeteknevForChange = vezeteknevForChange;
    }

    public String getKeresztnevForChange() {
        return keresztnevForChange;
    }

    public void setKeresztnevForChange(String keresztnevForChange) {
        this.keresztnevForChange = keresztnevForChange;
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

    public Orvos getSelectedOrvos() {
        return selectedOrvos;
    }

    public void setSelectedOrvos(Orvos selectedOrvos) {
        this.selectedOrvos = selectedOrvos;
    }

    public List<Orvos> getAllOrvos() {
        return allOrvos;
    }

    public void setAllOrvos(List<Orvos> allOrvos) {
        this.allOrvos = allOrvos;
    }

    public List<Orvos> getFilteredOrvos() {
        return filteredOrvos;
    }

    public void setFilteredOrvos(List<Orvos> filteredOrvos) {
        this.filteredOrvos = filteredOrvos;
    }

    public Orvos getSelectedOrvosForChange() {
        return selectedOrvosForChange;
    }

    public void setSelectedOrvosForChange(Orvos selectedOrvosForChange) {
        this.selectedOrvosForChange = selectedOrvosForChange;
    }

}
