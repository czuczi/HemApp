/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
import facade.AdminFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
@ManagedBean(name = "AdminController")
@SessionScoped
public class AdminController implements Serializable {

    @EJB
    private AdminFacade adminFacade;

    private List<Admin> allAdmin;
    private List<Admin> filteredAdmin;

    private Admin selectedAdmin;
    private String felhNev = "";
    private String felhNevForChange = "";
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
        allAdmin = adminFacade.findAll();
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/4dm1n1s7r470r/adminlogin?faces-redirect=true";
    }

    public void selectAdmin(SelectEvent selectEvent) {
        selectedAdmin = (Admin) selectEvent.getObject();
    }

    public void createAdmin() {
        Admin newAdmin;
        if (!jelszo.equals("")) {
            newAdmin = new Admin(felhNev, getMD5String(jelszo), vezeteknev, keresztnev);
            if (!email.isEmpty()) {
                newAdmin.setEmail(email);
            }
            if (!telefon.isEmpty()) {
                newAdmin.setTelefon(telefon);
            }
        } else {
            newAdmin = new Admin(felhNev, getMD5String("start123"), vezeteknev, keresztnev);
            newAdmin.setEmail(email);
            newAdmin.setTelefon(telefon);
        }
        adminFacade.create(newAdmin);
        init();
        felhNev = jelszo = vezeteknev = keresztnev = "";
        email = telefon = null;
        RequestContext.getCurrentInstance().update("tableForm");
        RequestContext.getCurrentInstance().update("newAdminForm");
    }

    public void deleteAdmin() {
        if (selectedAdmin == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon adminisztrátort!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        adminFacade.remove(selectedAdmin);
        init();
        RequestContext.getCurrentInstance().update("tableForm");
    }

    public void changePassword() {
        Admin aktAdmin = adminFacade.getByFelhNev(felhNevForChange).get(0);
        if (getMD5String(oldPassword).equals(aktAdmin.getJelszo())) {
            aktAdmin.setJelszo(getMD5String(newPassword));
            adminFacade.edit(aktAdmin);
            felhNevForChange = oldPassword = newPassword = "";
            RequestContext.getCurrentInstance().update("passwordForm");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Siker!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A régi jelszó nem megfelelő!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String getMD5String(String password) {
        String pwd = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes(), 0, password.length());
            pwd = new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return pwd;
    }

    public List<Admin> getAllAdmin() {
        return allAdmin;
    }

    public void setAllAdmin(List<Admin> allAdmin) {
        this.allAdmin = allAdmin;
    }

    public List<Admin> getFilteredAdmin() {
        return filteredAdmin;
    }

    public void setFilteredAdmin(List<Admin> filteredAdmin) {
        this.filteredAdmin = filteredAdmin;
    }

    public Admin getSelectedAdmin() {
        return selectedAdmin;
    }

    public void setSelectedAdmin(Admin selectedAdmin) {
        this.selectedAdmin = selectedAdmin;
    }

    public String getFelhNev() {
        return felhNev;
    }

    public void setFelhNev(String felhNev) {
        this.felhNev = felhNev;
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

    public String getFelhNevForChange() {
        return felhNevForChange;
    }

    public void setFelhNevForChange(String felhNevForChange) {
        this.felhNevForChange = felhNevForChange;
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

}
