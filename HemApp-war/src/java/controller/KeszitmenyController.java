/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.KeszKisz;
import entity.Keszitmeny;
import entity.Kiszereles;
import facade.KeszKiszFacade;
import facade.KeszitmenyFacade;
import facade.KiszerelesFacade;
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
@ManagedBean(name = "KeszitmenyController")
@SessionScoped
public class KeszitmenyController implements Serializable {

    @EJB
    private KeszitmenyFacade keszitmenyFacade;
    @EJB
    private KiszerelesFacade kiszerelesFacade;
    @EJB
    private KeszKiszFacade keszKiszFacade;

    private List<Keszitmeny> allKeszitmeny;
    private List<Kiszereles> allKiszereles;
    private List<KeszKisz> allKeszKisz;

    private Keszitmeny selectedKeszitmeny;
    private Kiszereles selectedKiszereles;
    private KeszKisz selectedKeszKisz;

    private String keszitmenyNev;
    private String kiszerelesNE;
    private String keszitmenyID;
    private String kiszerelesID;

    @PostConstruct
    public void init() {
        allKeszKisz = keszKiszFacade.findAll();
        allKeszitmeny = keszitmenyFacade.findAll();
        allKiszereles = kiszerelesFacade.findAll();
        keszitmenyID = allKeszitmeny.get(0).getId();
        kiszerelesID = allKiszereles.get(0).getId();
    }

    public void selectKeszitmeny(SelectEvent event) {
        selectedKeszitmeny = (Keszitmeny) event.getObject();
    }

    public void createKeszitmeny() {
        for (Keszitmeny k : allKeszitmeny) {
            if (k.getNev().equalsIgnoreCase(keszitmenyNev)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Már van ilyen készítmény!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        keszitmenyFacade.create(new Keszitmeny(keszitmenyNev));
        allKeszitmeny = keszitmenyFacade.findAll();
        keszitmenyNev = null;
        RequestContext.getCurrentInstance().execute("PF('newKeszitmenyDialogWidget').hide();");
        RequestContext.getCurrentInstance().update("newKeszitmenyForm");
    }

    public void deleteKeszitmeny() {
        if (selectedKeszitmeny == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon ki egy készítményt!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        keszitmenyFacade.remove(selectedKeszitmeny);
        allKeszitmeny = keszitmenyFacade.findAll();
        selectedKeszitmeny = null;
        RequestContext.getCurrentInstance().update("keszitmeny");
    }

    public void selectKiszereles(SelectEvent event) {
        selectedKiszereles = (Kiszereles) event.getObject();
    }

    public void createKiszereles() {
        for (Kiszereles k : allKiszereles) {
            try {
                if (k.getNE() == Integer.parseInt(kiszerelesNE)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Már van ilyen kiszerelés!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
            } catch (NumberFormatException e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Csak számokat adjon meg!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        kiszerelesFacade.create(new Kiszereles(Integer.parseInt(kiszerelesNE)));
        allKiszereles = kiszerelesFacade.findAll();
        kiszerelesNE = null;
        RequestContext.getCurrentInstance().execute("PF('newKiszerelesDialogWidget').hide();");
        RequestContext.getCurrentInstance().update("newKiszerelesForm");
    }

    public void deleteKiszereles() {
        if (selectedKeszKisz == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon ki egy készítményt kiszereléssel!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        kiszerelesFacade.remove(selectedKiszereles);
        allKeszKisz = keszKiszFacade.findAll();
        selectedKiszereles = null;
        RequestContext.getCurrentInstance().update("keszkisz");
    }

    public void createKeszKisz() {
        for (KeszKisz k : allKeszKisz) {
            if (k.getKeszitmenyID().getId().equalsIgnoreCase(keszitmenyID) && k.getKiszerelesID().getId().equalsIgnoreCase(kiszerelesID)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Már van ilyen kiszerelés az adott készítményből!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }
        KeszKisz keszKisz = new KeszKisz();
        keszKisz.setKeszitmenyID(keszitmenyFacade.getByID(keszitmenyID).get(0));
        keszKisz.setKiszerelesID(kiszerelesFacade.getByID(kiszerelesID).get(0));
        keszKiszFacade.create(keszKisz);
        allKeszKisz = keszKiszFacade.findAll();
        keszitmenyID = allKeszitmeny.get(0).getId();
        kiszerelesID = allKiszereles.get(0).getId();
        RequestContext.getCurrentInstance().execute("PF('newKeszKiszDialogWidget').hide();");
        RequestContext.getCurrentInstance().update("newKeszKiszForm");
    }

    public void selectKeszKisz(SelectEvent event) {
        selectedKeszKisz = (KeszKisz) event.getObject();
    }

    public void deleteKeszKisz() {
        if (selectedKeszKisz == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Kérem válasszon ki egy készítményt!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        keszKiszFacade.remove(selectedKeszKisz);
        allKeszKisz = keszKiszFacade.findAll();
        selectedKeszKisz = null;
        RequestContext.getCurrentInstance().update("keszkisz");
    }

    public List<Keszitmeny> getAllKeszitmeny() {
        return allKeszitmeny;
    }

    public void setAllKeszitmeny(List<Keszitmeny> allKeszitmeny) {
        this.allKeszitmeny = allKeszitmeny;
    }

    public List<Kiszereles> getAllKiszereles() {
        return allKiszereles;
    }

    public void setAllKiszereles(List<Kiszereles> allKiszereles) {
        this.allKiszereles = allKiszereles;
    }

    public List<KeszKisz> getAllKeszKisz() {
        return allKeszKisz;
    }

    public void setAllKeszKisz(List<KeszKisz> allKeszKisz) {
        this.allKeszKisz = allKeszKisz;
    }

    public Keszitmeny getSelectedKeszitmeny() {
        return selectedKeszitmeny;
    }

    public void setSelectedKeszitmeny(Keszitmeny selectedKeszitmeny) {
        this.selectedKeszitmeny = selectedKeszitmeny;
    }

    public Kiszereles getSelectedKiszereles() {
        return selectedKiszereles;
    }

    public void setSelectedKiszereles(Kiszereles selectedKiszereles) {
        this.selectedKiszereles = selectedKiszereles;
    }

    public KeszKisz getSelectedKeszKisz() {
        return selectedKeszKisz;
    }

    public void setSelectedKeszKisz(KeszKisz selectedKeszKisz) {
        this.selectedKeszKisz = selectedKeszKisz;
    }

    public String getKeszitmenyNev() {
        return keszitmenyNev;
    }

    public void setKeszitmenyNev(String keszitmenyNev) {
        this.keszitmenyNev = keszitmenyNev;
    }

    public String getKiszerelesNE() {
        return kiszerelesNE;
    }

    public void setKiszerelesNE(String kiszerelesNE) {
        this.kiszerelesNE = kiszerelesNE;
    }

    public String getKeszitmenyID() {
        return keszitmenyID;
    }

    public void setKeszitmenyID(String keszitmenyID) {
        this.keszitmenyID = keszitmenyID;
    }

    public String getKiszerelesID() {
        return kiszerelesID;
    }

    public void setKiszerelesID(String kiszerelesID) {
        this.kiszerelesID = kiszerelesID;
    }

}
