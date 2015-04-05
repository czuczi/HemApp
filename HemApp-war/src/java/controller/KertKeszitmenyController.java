/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Beteg;
import entity.KertKeszKisz;
import entity.KeszKisz;
import entity.Kiszereles;
import facade.BetegFacade;
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
@ManagedBean(name = "KertKeszitmenyController")
@SessionScoped
public class KertKeszitmenyController implements Serializable {
    
    @ManagedProperty("#{LoginController}")
    private LoginController loginController;
    
    @EJB
    private KertKeszKiszFacade kertKeszKiszFacade;
    @EJB
    private BetegFacade betegFacade;
    
    private List<KertKeszKisz> kertKeszKiszList;
    private List<Beteg> betegekForOrvos;
    
    private KeszKisz selectedKeszKisz;
    private Beteg selectedBeteg;
    
    private String selectedBetegID;
    private String selectedKeszKiszID;
    private String selectedDarabSzam;
    private Date selectedDate;
    
    @PostConstruct
    public void init() {
        betegekForOrvos = new LinkedList<>(loginController.getOrvos().getBetegCollection());
        selectedBeteg = betegekForOrvos.get(0);
        selectedBetegID = selectedBeteg.getId();
        selectedDate = new Date();
        kertKeszKiszList = kertKeszKiszFacade.getActualByBeteg(selectedBeteg);
    }

    public void updateSelectedBeteg() {
        selectedBeteg = betegFacade.getByID(selectedBetegID).get(0);
        kertKeszKiszList = kertKeszKiszFacade.getActualByBeteg(selectedBeteg);
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

    public String getSelectedBetegID() {
        return selectedBetegID;
    }

    public void setSelectedBetegID(String selectedBetegID) {
        this.selectedBetegID = selectedBetegID;
    }
    
}
