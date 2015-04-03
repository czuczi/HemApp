/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.RaktarKeszKisz;
import facade.RaktarKeszKiszFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author gczuczor
 */
@Stateless
@ManagedBean(name = "RaktarController")
@SessionScoped
public class RaktarController implements Serializable {
    @EJB
    RaktarKeszKiszFacade raktarKeszKiszFacade;
    
    private List<RaktarKeszKisz> allRaktartKeszKisz;
    
    private RaktarKeszKisz selectedRaktarKeszKisz;
    
    @PostConstruct
    public void init() {
        allRaktartKeszKisz = raktarKeszKiszFacade.findAll();
    }

    public void selectRaktarKeszKisz(SelectEvent event) {
        selectedRaktarKeszKisz = (RaktarKeszKisz)event.getObject();
    }
    
    public List<RaktarKeszKisz> getAllRaktartKeszKisz() {
        return allRaktartKeszKisz;
    }

    public void setAllRaktartKeszKisz(List<RaktarKeszKisz> allRaktartKeszKisz) {
        this.allRaktartKeszKisz = allRaktartKeszKisz;
    }

    public RaktarKeszKisz getSelectedRaktarKeszKisz() {
        return selectedRaktarKeszKisz;
    }

    public void setSelectedRaktarKeszKisz(RaktarKeszKisz selectedRaktarKeszKisz) {
        this.selectedRaktarKeszKisz = selectedRaktarKeszKisz;
    }

}
