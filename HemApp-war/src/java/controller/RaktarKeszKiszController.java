/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.KertKeszKisz;
import entity.KeszKisz;
import entity.Kiszereles;
import entity.Orvos;
import entity.RaktarKeszKisz;
import facade.InjekciotortenetFacade;
import facade.KertKeszKiszFacade;
import facade.KeszKiszFacade;
import facade.OrvosFacade;
import facade.RaktarKeszKiszFacade;
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
@ManagedBean(name = "RaktarKeszKiszController")
@SessionScoped
public class RaktarKeszKiszController implements Serializable {
    
    @ManagedProperty("#{LoginController}")
    private LoginController loginController;
    
    @EJB
    private KeszKiszFacade keszKiszFacade;
    @EJB
    private RaktarKeszKiszFacade raktarKeszKiszFacade;
    @EJB
    private OrvosFacade orvosFacade;
    
    private List<KeszKisz> keszKiszList;
    private List<RaktarKeszKisz> raktarKeszKiszList;
    
    private KeszKisz selectedKeszKisz;
    
    private String selectedKeszKiszID;
    private String selectedDarabSzam;
    private String selectedSorozatszam;
    
    @PostConstruct
    public void init() {
        keszKiszList = keszKiszFacade.findAll();
        selectedKeszKisz = keszKiszList.get(0);
        raktarKeszKiszList = new LinkedList<>(loginController.getOrvos().getRaktarKeszKiszCollection());
    }
    
    public void felvetel() {
        try {
            Integer.parseInt(selectedDarabSzam);
        } catch (NumberFormatException e) {
            RequestContext.getCurrentInstance().execute("PF('raktarDialogWidget').hide();");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Csak számokat adjon meg a darabszámnál!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        if(selectedSorozatszam == null || selectedSorozatszam.length() == 0) {
            RequestContext.getCurrentInstance().execute("PF('raktarDialogWidget').hide();");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "A sorozatszám megadása kötelező!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        RaktarKeszKisz raktarKeszKisz;
        List<RaktarKeszKisz> seged = raktarKeszKiszFacade.getByOrvosKeszKiszSorozatszam(loginController.getOrvos(), selectedKeszKisz, selectedSorozatszam);
        if(seged != null && !seged.isEmpty()) {
            raktarKeszKisz = seged.get(0);
            raktarKeszKisz.setDarab(raktarKeszKisz.getDarab() + Integer.parseInt(selectedDarabSzam));
            raktarKeszKiszFacade.edit(raktarKeszKisz);
        } else {
            raktarKeszKisz = new RaktarKeszKisz(selectedSorozatszam, Integer.parseInt(selectedDarabSzam));
            raktarKeszKisz.setOrvosID(loginController.getOrvos());
            raktarKeszKisz.setKeszKiszID(selectedKeszKisz);
            raktarKeszKiszFacade.create(raktarKeszKisz);
        }
        selectedDarabSzam = selectedSorozatszam = null;
        Orvos orvos = orvosFacade.getByID(loginController.getOrvos().getId()).get(0);
        raktarKeszKiszList = new LinkedList<>(orvos.getRaktarKeszKiszCollection());
        RequestContext.getCurrentInstance().execute("PF('raktarDialogWidget').hide();");
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

    public String getSelectedSorozatszam() {
        return selectedSorozatszam;
    }

    public void setSelectedSorozatszam(String selectedSorozatszam) {
        this.selectedSorozatszam = selectedSorozatszam;
    }

    public List<RaktarKeszKisz> getRaktarKeszKiszList() {
        return raktarKeszKiszList;
    }

    public void setRaktarKeszKiszList(List<RaktarKeszKisz> raktarKeszKiszList) {
        this.raktarKeszKiszList = raktarKeszKiszList;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
    
}
