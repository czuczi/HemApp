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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.mobile.event.SwipeEvent;

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

    private List<Beteg> allBeteg;

    private Beteg actBeteg;

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

    @PostConstruct
    public void init() {
        allBeteg = betegFacade.findAll();
        if (actBeteg == null) {
            actBeteg = loginController.getBeteg();
            felhNevForChange = actBeteg.getFelhnev();
            emailForChange = actBeteg.getEmail();
            telefonForChange = actBeteg.getTelefon();
        }
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
        init();
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
        init();
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

}
