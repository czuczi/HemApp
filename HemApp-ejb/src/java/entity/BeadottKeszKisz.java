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
@Table(name = "beadott_kesz_kisz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BeadottKeszKisz.findAll", query = "SELECT b FROM BeadottKeszKisz b"),
    @NamedQuery(name = "BeadottKeszKisz.findById", query = "SELECT b FROM BeadottKeszKisz b WHERE b.id = :id"),
    @NamedQuery(name = "BeadottKeszKisz.findByDatum", query = "SELECT b FROM BeadottKeszKisz b WHERE b.datum = :datum"),
    @NamedQuery(name = "BeadottKeszKisz.findBySorozatszam", query = "SELECT b FROM BeadottKeszKisz b WHERE b.sorozatszam = :sorozatszam")})
public class BeadottKeszKisz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Sorozatszam")
    private String sorozatszam;
    @JoinColumn(name = "Beteg_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Beteg betegID;
    @JoinColumn(name = "Kesz_Kisz_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private KeszKisz keszKiszID;

    public BeadottKeszKisz() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public BeadottKeszKisz(Date datum, String sorozatszam) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.datum = datum;
        this.sorozatszam = sorozatszam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getSorozatszam() {
        return sorozatszam;
    }

    public void setSorozatszam(String sorozatszam) {
        this.sorozatszam = sorozatszam;
    }

    public Beteg getBetegID() {
        return betegID;
    }

    public void setBetegID(Beteg betegID) {
        this.betegID = betegID;
    }

    public KeszKisz getKeszKiszID() {
        return keszKiszID;
    }

    public void setKeszKiszID(KeszKisz keszKiszID) {
        this.keszKiszID = keszKiszID;
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
        if (!(object instanceof BeadottKeszKisz)) {
            return false;
        }
        BeadottKeszKisz other = (BeadottKeszKisz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BeadottKeszKisz[ id=" + id + " ]";
    }
    
}
