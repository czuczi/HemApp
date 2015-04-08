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
import entity.Orvos;
import entity.OtthonKeszKisz;
import entity.RaktarKeszKisz;
import facade.BetegFacade;
import facade.InjekciotortenetFacade;
import facade.KertKeszKiszFacade;
import facade.KeszKiszFacade;
import facade.OrvosFacade;
import facade.OtthonKeszKiszFacade;
import facade.RaktarKeszKiszFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
@ManagedBean(name = "KeszitmenyKiadasController")
@SessionScoped
public class KeszitmenyKiadasController implements Serializable {

    @ManagedProperty("#{LoginController}")
    private LoginController loginController;

    @EJB
    private KeszKiszFacade keszKiszFacade;
    @EJB
    private RaktarKeszKiszFacade raktarKeszKiszFacade;
    @EJB
    private OrvosFacade orvosFacade;
    @EJB
    private BetegFacade betegFacade;
    @EJB
    private OtthonKeszKiszFacade otthonKeszKiszFacade;

    private List<KeszKisz> keszKiszList;
    private List<RaktarKeszKisz> raktarKeszKiszList;
    private List<Beteg> betegekForOrvos;
    private List<OtthonKeszKisz> otthonKeszKiszByBeteg;
    private HashSet<String> sorozatSzamSet;

    private KeszKisz selectedKeszKisz;
    private Beteg selectedBeteg;

    private String selectedBetegID;
    private String selectedKeszKiszID;
    private String selectedDarabSzam;
    private String selectedSorozatszam;

    @PostConstruct
    public void init() {
        keszKiszList = raktarKeszKiszFacade.getKeszKiszByOrvos(loginController.getOrvos());
        if (!keszKiszList.isEmpty()) {
            selectedKeszKisz = keszKiszList.get(0);
        }
        raktarKeszKiszList = new LinkedList<>(loginController.getOrvos().getRaktarKeszKiszCollection());
        betegekForOrvos = new LinkedList<>(loginController.getOrvos().getBetegCollection());
        if (!betegekForOrvos.isEmpty()) {
            selectedBetegID = betegekForOrvos.get(0).getId();
            selectedBeteg = betegekForOrvos.get(0);
            otthonKeszKiszByBeteg = new LinkedList<>(selectedBeteg.getOtthonKeszKiszCollection());
        }
        sorozatSzamSet = new HashSet<>();
        for (RaktarKeszKisz r : raktarKeszKiszList) {
            if (r.getKeszKiszID().equals(selectedKeszKisz)) {
                sorozatSzamSet.add(r.getSorozatszam());
            }
        }
    }

    public void updateSelectedBeteg() {
        selectedBeteg = betegFacade.getByID(selectedBetegID).get(0);
        otthonKeszKiszByBeteg = new LinkedList<>(selectedBeteg.getOtthonKeszKiszCollection());
    }

    public void kiadas() {
        int db;
        try {
            db = Integer.parseInt(selectedDarabSzam);
        } catch (NumberFormatException e) {
            RequestContext.getCurrentInstance().execute("PF('kiadasDialogWidget').hide();");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Csak számokat adjon meg a darabszámnál!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        for (RaktarKeszKisz r : raktarKeszKiszList) {
            if (r.getKeszKiszID().equals(selectedKeszKisz)) {
                if (db > r.getDarab()) {
                    RequestContext.getCurrentInstance().execute("PF('kiadasDialogWidget').hide();");
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Maximum " + r.getDarab() + " készítmény áll rendelkezésre!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    RequestContext.getCurrentInstance().execute("PF('kiadasDialogWidget').hide();");
                    return;
                } else {
                    OtthonKeszKisz otthonKeszKisz;
                    List<OtthonKeszKisz> seged = otthonKeszKiszFacade.getByBetegKeszKiszSorozatszam(selectedBeteg, selectedKeszKisz, selectedSorozatszam);
                    if (seged != null && !seged.isEmpty()) {
                        otthonKeszKisz = seged.get(0);
                        otthonKeszKisz.setDarab(otthonKeszKisz.getDarab() + Integer.parseInt(selectedDarabSzam));
                        otthonKeszKiszFacade.edit(otthonKeszKisz);
                    } else {
                        otthonKeszKisz = new OtthonKeszKisz(selectedSorozatszam, db);
                        otthonKeszKisz.setKeszKiszID(selectedKeszKisz);
                        otthonKeszKisz.setBetegID(selectedBeteg);
                        otthonKeszKiszFacade.create(otthonKeszKisz);
                    }
                    if (db == r.getDarab()) {
                        raktarKeszKiszFacade.remove(r);
                    } else {
                        r.setDarab(r.getDarab() - db);
                        raktarKeszKiszFacade.edit(r);
                    }
                }
                selectedDarabSzam = selectedSorozatszam = null;
                Orvos orvos = orvosFacade.getByID(loginController.getOrvos().getId()).get(0);
                raktarKeszKiszList = new LinkedList<>(orvos.getRaktarKeszKiszCollection());
                Beteg beteg = betegFacade.getByID(selectedBetegID).get(0);
                otthonKeszKiszByBeteg = new LinkedList<>(beteg.getOtthonKeszKiszCollection());
                sorozatSzamSet = new HashSet<>();
                for (RaktarKeszKisz r1 : raktarKeszKiszList) {
                    if (r1.getKeszKiszID().equals(selectedKeszKisz)) {
                        sorozatSzamSet.add(r1.getSorozatszam());
                    }
                }
                RequestContext.getCurrentInstance().execute("PF('kiadasDialogWidget').hide();");
                return;
            }
        }

    }

    public void updateSelectedKeszKisz() {
        selectedKeszKisz = keszKiszFacade.getByID(selectedKeszKiszID).get(0);
        sorozatSzamSet = new HashSet<>();
        for (RaktarKeszKisz r : raktarKeszKiszList) {
            if (r.getKeszKiszID().equals(selectedKeszKisz)) {
                sorozatSzamSet.add(r.getSorozatszam());
            }
        }
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

    public List<Beteg> getBetegekForOrvos() {
        return betegekForOrvos;
    }

    public void setBetegekForOrvos(List<Beteg> betegekForOrvos) {
        this.betegekForOrvos = betegekForOrvos;
    }

    public List<OtthonKeszKisz> getOtthonKeszKiszByBeteg() {
        return otthonKeszKiszByBeteg;
    }

    public void setOtthonKeszKiszByBeteg(List<OtthonKeszKisz> otthonKeszKiszByBeteg) {
        this.otthonKeszKiszByBeteg = otthonKeszKiszByBeteg;
    }

    public HashSet<String> getSorozatSzamSet() {
        return sorozatSzamSet;
    }

    public void setSorozatSzamSet(HashSet<String> sorozatSzamSet) {
        this.sorozatSzamSet = sorozatSzamSet;
    }

}
