/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gczuczor
 */
@Entity
@Table(name = "injekciotortenet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Injekciotortenet.findAll", query = "SELECT i FROM Injekciotortenet i"),
    @NamedQuery(name = "Injekciotortenet.findById", query = "SELECT i FROM Injekciotortenet i WHERE i.id = :id"),
    @NamedQuery(name = "Injekciotortenet.findByKezdetdatum", query = "SELECT i FROM Injekciotortenet i WHERE i.kezdetdatum = :kezdetdatum"),
    @NamedQuery(name = "Injekciotortenet.findByVegedatum", query = "SELECT i FROM Injekciotortenet i WHERE i.vegedatum = :vegedatum"),
    @NamedQuery(name = "Injekciotortenet.findByMennyiseg", query = "SELECT i FROM Injekciotortenet i WHERE i.mennyiseg = :mennyiseg"),
    @NamedQuery(name = "Injekciotortenet.findActualByBeteg", query = "SELECT i FROM Injekciotortenet i WHERE i.betegID = :betegID AND i.vegedatum IS NULL")})
public class Injekciotortenet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kezdetdatum")
    @Temporal(TemporalType.DATE)
    private Date kezdetdatum;
    @Column(name = "Vegedatum")
    @Temporal(TemporalType.DATE)
    private Date vegedatum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Mennyiseg")
    private int mennyiseg;
    @JoinColumn(name = "Beteg_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Beteg betegID;
    @JoinColumn(name = "Keszitmeny_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Keszitmeny keszitmenyID;

    public Injekciotortenet() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public Injekciotortenet(Date kezdetdatum, int mennyiseg) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.kezdetdatum = kezdetdatum;
        this.mennyiseg = mennyiseg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getKezdetdatum() {
        return kezdetdatum;
    }

    public void setKezdetdatum(Date kezdetdatum) {
        this.kezdetdatum = kezdetdatum;
    }

    public Date getVegedatum() {
        return vegedatum;
    }

    public void setVegedatum(Date vegedatum) {
        this.vegedatum = vegedatum;
    }

    public int getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(int mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public Beteg getBetegID() {
        return betegID;
    }

    public void setBetegID(Beteg betegID) {
        this.betegID = betegID;
    }

    public Keszitmeny getKeszitmenyID() {
        return keszitmenyID;
    }

    public void setKeszitmenyID(Keszitmeny keszitmenyID) {
        this.keszitmenyID = keszitmenyID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Injekciotortenet)) {
            return false;
        }
        Injekciotortenet other = (Injekciotortenet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Injekciotortenet[ id=" + id + " ]";
    }

}
