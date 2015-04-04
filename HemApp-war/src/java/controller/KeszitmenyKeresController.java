/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.KertKeszKisz;
import entity.KeszKisz;
import entity.Kiszereles;
import facade.InjekciotortenetFacade;
import facade.KertKeszKiszFacade;
import facade.KeszKiszFacade;
import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author gczuczor
 */
@Stateless
@ManagedBean(name = "KeszitmenyKeresController")
@SessionScoped
public class KeszitmenyKeresController implements Serializable {
    
    @ManagedProperty("#{LoginController}")
    private LoginController loginController;
    
    @EJB
    private InjekciotortenetFacade injekciotortenetFacade;
    @EJB
    private KeszKiszFacade keszKiszFacade;
    @EJB
    private KertKeszKiszFacade kertKeszKiszFacade;
    
    private List<KeszKisz> keszKiszList;
    private List<KertKeszKisz> kertKeszKiszList;
    
    private KeszKisz selectedKeszKisz;
    
    private String selectedKeszKiszID;
    private String selectedDarabSzam;
    private Date selectedDate;
    
    @PostConstruct
    public void init() {
        keszKiszList = new LinkedList<>(injekciotortenetFacade.getActualByBeteg(loginController.getBeteg()).get(0).getKeszitmenyID().getKeszKiszCollection());
        selectedKeszKisz = keszKiszList.get(0);
        selectedDate = new Date();
        kertKeszKiszList = kertKeszKiszFacade.getActual();
    }
    
    public void createKeres() {
        try {
            Integer.parseInt(selectedDarabSzam);
        } catch (NumberFormatException e) {
            RequestContext.getCurrentInstance().execute("PF('keresDialogWidget').hide();");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Csak számokat adjon meg a darabszámnál!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        
        if(selectedDate.getTime() < new Date().getTime()) {
            RequestContext.getCurrentInstance().execute("PF('keresDialogWidget').hide();");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Csak jövőbeni időpontot adhat meg!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        KertKeszKisz kertKeszKisz = new KertKeszKisz(Integer.parseInt(selectedDarabSzam));
        kertKeszKisz.setKeszKiszID(selectedKeszKisz);
        kertKeszKisz.setBetegID(loginController.getBeteg());
        kertKeszKisz.setIdopont(selectedDate);
        kertKeszKiszFacade.create(kertKeszKisz);
        kertKeszKiszList = kertKeszKiszFacade.getActual();
        RequestContext.getCurrentInstance().execute("PF('keresDialogWidget').hide();");
    }
    
    public void updateSelectedKeszKisz() {
        selectedKeszKisz = keszKiszFacade.getByID(selectedKeszKiszID).get(0);
    }

    public List<KeszKisz> getKeszKiszList() {
        return keszKiszList;
    }

    public void setKeszKiszList(List<KeszKisz> keszKiszList) {
        this.keszKiszList = keszKiszList;
    }

    public String getSelectedKeszKiszID() {
        return selectedKeszKiszID;
    }

    public void setSelectedKeszKiszID(String selectedKeszKiszID) {
        this.selectedKeszKiszID = selectedKeszKiszID;
    }

    public KeszKisz getSelectedKeszKisz() {
        return selectedKeszKisz;
    }

    public void setSelectedKeszKisz(KeszKisz selectedKeszKisz) {
        this.selectedKeszKisz = selectedKeszKisz;
    }

    public String getSelectedDarabSzam() {
        return selectedDarabSzam;
    }

    public void setSelectedDarabSzam(String selectedDarabSzam) {
        this.selectedDarabSzam = selectedDarabSzam;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<KertKeszKisz> getKertKeszKiszList() {
        return kertKeszKiszList;
    }

    public void setKertKeszKiszList(List<KertKeszKisz> kertKeszKiszList) {
        this.kertKeszKiszList = kertKeszKiszList;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
    
}
