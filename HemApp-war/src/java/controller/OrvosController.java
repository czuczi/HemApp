/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Orvos;
import facade.OrvosFacade;
import java.io.Serializable;
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
@Stateless
@ManagedBean(name = "OrvosController")
@SessionScoped
public class OrvosController implements Serializable{

    @EJB
    private LoginController loginController;

    @EJB
    private OrvosFacade orvosFacade;

    private List<Orvos> allOrvos;
    private List<Orvos> filteredOrvos;

    private Orvos selectedOrvos;

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

    @PostConstruct
    public void init() {
        allOrvos = orvosFacade.findAll();
    }

    public void selectOrvos(SelectEvent selectEvent) {
        selectedOrvos = (Orvos) selectEvent.getObject();
        felhNevForChange = selectedOrvos.getFelhnev();
        vezeteknevForChange = selectedOrvos.getVezeteknev();
        keresztnevForChange = selectedOrvos.getKeresztnev();
        emailForChange = selectedOrvos.getEmail();
        telefonForChange = selectedOrvos.getTelefon();
        RequestContext.getCurrentInstance().update("editOrvosForm");
    }

    public void createOrvos() {
        for (Orvos orvos : allOrvos) {
            if (orvos.getFelhnev().equals(felhNev)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A felhasználónév foglalt!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        Orvos newOrvos;
        if (!jelszo.equals("")) {
            newOrvos = new Orvos(felhNev, loginController.getMD5String(jelszo), vezeteknev, keresztnev);
            if (!email.isEmpty()) {
                newOrvos.setEmail(email);
            }
            if (!telefon.isEmpty()) {
                newOrvos.setTelefon(telefon);
            }
        } else {
            newOrvos = new Orvos(felhNev, loginController.getMD5String("start123"), vezeteknev, keresztnev);
            if (!email.isEmpty()) {
                newOrvos.setEmail(email);
            }
            if (!telefon.isEmpty()) {
                newOrvos.setTelefon(telefon);
            }
        }
        orvosFacade.create(newOrvos);
        init();
        felhNev = jelszo = vezeteknev = keresztnev = "";
        email = telefon = null;
        RequestContext.getCurrentInstance().update("tableForm");
        RequestContext.getCurrentInstance().update("newOrvosForm");
    }

    public void editOrvosShow() {
        if (selectedOrvos == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon orvost!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        RequestContext.getCurrentInstance().execute("PF('editOrvosDialogWidget').show()");
    }
    
    public void editOrvos() {
        for (Orvos orvos : allOrvos) {
            if (orvos.getFelhnev().equals(felhNevForChange) && !selectedOrvos.getFelhnev().equals(felhNevForChange)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A felhasználónév foglalt!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        if (selectedOrvos == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon orvost!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        selectedOrvos.setFelhnev(felhNevForChange);
        selectedOrvos.setVezeteknev(vezeteknevForChange);
        selectedOrvos.setKeresztnev(keresztnevForChange);
        selectedOrvos.setEmail(emailForChange);
        selectedOrvos.setTelefon(telefonForChange);
        
        if(emailForChange.isEmpty()) {
            selectedOrvos.setEmail(null);
        }
        
        if(telefonForChange.isEmpty()) {
            selectedOrvos.setTelefon(null);
        }
        
        orvosFacade.edit(selectedOrvos);
        init();
        RequestContext.getCurrentInstance().update("tableForm");
        RequestContext.getCurrentInstance().update("editOrvosForm");
    }

    public void deleteOrvos() {
        if (selectedOrvos == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon orvost!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        orvosFacade.remove(selectedOrvos);
        init();
        RequestContext.getCurrentInstance().update("tableForm");
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

}
