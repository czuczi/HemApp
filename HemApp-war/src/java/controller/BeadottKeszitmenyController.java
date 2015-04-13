/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.BeadottKeszKisz;
import entity.Beteg;
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
@ManagedBean(name = "BeadottKeszitmenyController")
@SessionScoped
public class BeadottKeszitmenyController implements Serializable {

    @ManagedProperty("#{LoginController}")
    private LoginController loginController;

    @EJB
    private BetegFacade betegFacade;
    @EJB
    private BeadottKeszKiszFacade beadottKeszKiszFacade;

    private List<BeadottKeszKisz> beadottKeszKiszList;
    private List<Beteg> betegList;

    private Beteg selectedBeteg;

    private String selectedBetegID;
    private Date selectedDate;

    @PostConstruct
    public void init() {
        selectedDate = new Date();
        betegList = new LinkedList<>(loginController.getOrvos().getBetegCollection());
        if (!betegList.isEmpty()) {
            selectedBeteg = betegList.get(0);
            beadottKeszKiszList = new LinkedList<>(selectedBeteg.getBeadottKeszKiszCollection());
            Collections.sort(beadottKeszKiszList, new Comparator<BeadottKeszKisz>() {

                @Override
                public int compare(BeadottKeszKisz o1, BeadottKeszKisz o2) {
                    return o2.getDatum().compareTo(o1.getDatum());
                }

            });
        }
    }

    public void updateSelectedBeteg() {
        selectedBeteg = betegFacade.getByID(selectedBetegID).get(0);
        beadottKeszKiszList = new LinkedList<>(selectedBeteg.getBeadottKeszKiszCollection());
            Collections.sort(beadottKeszKiszList, new Comparator<BeadottKeszKisz>() {

                @Override
                public int compare(BeadottKeszKisz o1, BeadottKeszKisz o2) {
                    return o2.getDatum().compareTo(o1.getDatum());
                }

            });
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

    public List<BeadottKeszKisz> getBeadottKeszKiszList() {
        return beadottKeszKiszList;
    }

    public void setBeadottKeszKiszList(List<BeadottKeszKisz> beadottKeszKiszList) {
        this.beadottKeszKiszList = beadottKeszKiszList;
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

    public List<Beteg> getBetegList() {
        return betegList;
    }

    public void setBetegList(List<Beteg> betegList) {
        this.betegList = betegList;
    }

}
