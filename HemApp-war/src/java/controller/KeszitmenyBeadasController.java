/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.BeadottKeszKisz;
import entity.OtthonKeszKisz;
import facade.BeadottKeszKiszFacade;
import facade.BetegFacade;
import facade.OrvosFacade;
import facade.OtthonKeszKiszFacade;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
@ManagedBean(name = "KeszitmenyBeadasController")
@SessionScoped
public class KeszitmenyBeadasController implements Serializable {

    @ManagedProperty("#{LoginController}")
    private LoginController loginController;

    @EJB
    private BetegFacade betegFacade;
    @EJB
    private OtthonKeszKiszFacade otthonKeszKiszFacade;
    @EJB
    private BeadottKeszKiszFacade beadottKeszKiszFacade;

    private List<OtthonKeszKisz> otthonKeszKiszList;
    private List<BeadottKeszKisz> beadottKeszKiszList;

    private OtthonKeszKisz selectedOtthonKeszKisz;

    private String selectedOtthonKeszKiszID;
    private Date selectedDate;

    @PostConstruct
    public void init() {
        selectedDate = new Date();
        otthonKeszKiszList = new LinkedList<>(loginController.getBeteg().getOtthonKeszKiszCollection());
        beadottKeszKiszList = new LinkedList<>(loginController.getBeteg().getBeadottKeszKiszCollection());
        if (!otthonKeszKiszList.isEmpty()) {
            selectedOtthonKeszKisz = otthonKeszKiszList.get(0);
            selectedOtthonKeszKiszID = selectedOtthonKeszKisz.getId();
        }
        Collections.sort(beadottKeszKiszList, new Comparator<BeadottKeszKisz>() {

            @Override
            public int compare(BeadottKeszKisz o1, BeadottKeszKisz o2) {
                return o2.getDatum().compareTo(o1.getDatum());
            }

        });
    }

    public void beadas() {
        if (selectedDate.getTime() > new Date().getTime()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Csak múltbeli időpontot adjon meg!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().execute("PF('beadasDialogWidget').hide();");
            return;
        }
        if (selectedOtthonKeszKisz.getDarab() > 1) {
            selectedOtthonKeszKisz.setDarab(selectedOtthonKeszKisz.getDarab() - 1);
            otthonKeszKiszFacade.edit(selectedOtthonKeszKisz);
        } else {
            otthonKeszKiszFacade.remove(selectedOtthonKeszKisz);
        }
        BeadottKeszKisz beadottKeszKisz = new BeadottKeszKisz(selectedDate, selectedOtthonKeszKisz.getSorozatszam());
        beadottKeszKisz.setBetegID(loginController.getBeteg());
        beadottKeszKisz.setKeszKiszID(selectedOtthonKeszKisz.getKeszKiszID());
        beadottKeszKiszFacade.create(beadottKeszKisz);

        otthonKeszKiszList = new LinkedList<>(betegFacade.getByID(loginController.getBeteg().getId()).get(0).getOtthonKeszKiszCollection());
        beadottKeszKiszList = new LinkedList<>(betegFacade.getByID(loginController.getBeteg().getId()).get(0).getBeadottKeszKiszCollection());
        loginController.getBeteg().setOtthonKeszKiszCollection(otthonKeszKiszList);
        loginController.getBeteg().setBeadottKeszKiszCollection(beadottKeszKiszList);
        if (!otthonKeszKiszList.isEmpty()) {
            selectedOtthonKeszKisz = otthonKeszKiszList.get(0);
            selectedOtthonKeszKiszID = selectedOtthonKeszKisz.getId();
        }
        Collections.sort(beadottKeszKiszList, new Comparator<BeadottKeszKisz>() {

            @Override
            public int compare(BeadottKeszKisz o1, BeadottKeszKisz o2) {
                return o2.getDatum().compareTo(o1.getDatum());
            }

        });
    }

    public void updateSelectedOtthonKeszKisz() {
        selectedOtthonKeszKisz = otthonKeszKiszFacade.getByID(selectedOtthonKeszKiszID).get(0);
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<OtthonKeszKisz> getOtthonKeszKiszList() {
        return otthonKeszKiszList;
    }

    public void setOtthonKeszKiszList(List<OtthonKeszKisz> otthonKeszKiszList) {
        this.otthonKeszKiszList = otthonKeszKiszList;
    }

    public List<BeadottKeszKisz> getBeadottKeszKiszList() {
        return beadottKeszKiszList;
    }

    public void setBeadottKeszKiszList(List<BeadottKeszKisz> beadottKeszKiszList) {
        this.beadottKeszKiszList = beadottKeszKiszList;
    }

    public OtthonKeszKisz getSelectedOtthonKeszKisz() {
        return selectedOtthonKeszKisz;
    }

    public void setSelectedOtthonKeszKisz(OtthonKeszKisz selectedOtthonKeszKisz) {
        this.selectedOtthonKeszKisz = selectedOtthonKeszKisz;
    }

    public String getSelectedOtthonKeszKiszID() {
        return selectedOtthonKeszKiszID;
    }

    public void setSelectedOtthonKeszKiszID(String selectedOtthonKeszKiszID) {
        this.selectedOtthonKeszKiszID = selectedOtthonKeszKiszID;
    }

}
