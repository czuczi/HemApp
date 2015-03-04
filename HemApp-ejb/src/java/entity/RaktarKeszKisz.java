/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gczuczor
 */
@Entity
@Table(name = "raktar_kesz_kisz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RaktarKeszKisz.findAll", query = "SELECT r FROM RaktarKeszKisz r"),
    @NamedQuery(name = "RaktarKeszKisz.findById", query = "SELECT r FROM RaktarKeszKisz r WHERE r.id = :id"),
    @NamedQuery(name = "RaktarKeszKisz.findBySorozatszam", query = "SELECT r FROM RaktarKeszKisz r WHERE r.sorozatszam = :sorozatszam"),
    @NamedQuery(name = "RaktarKeszKisz.findByDarab", query = "SELECT r FROM RaktarKeszKisz r WHERE r.darab = :darab")})
public class RaktarKeszKisz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Sorozatszam")
    private String sorozatszam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "darab")
    private int darab;
    @JoinColumn(name = "Kesz_Kisz_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private KeszKisz keszKiszID;

    public RaktarKeszKisz() {
    }

    public RaktarKeszKisz(String id) {
        this.id = id;
    }

    public RaktarKeszKisz(String id, String sorozatszam, int darab) {
        this.id = id;
        this.sorozatszam = sorozatszam;
        this.darab = darab;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSorozatszam() {
        return sorozatszam;
    }

    public void setSorozatszam(String sorozatszam) {
        this.sorozatszam = sorozatszam;
    }

    public int getDarab() {
        return darab;
    }

    public void setDarab(int darab) {
        this.darab = darab;
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
        if (!(object instanceof RaktarKeszKisz)) {
            return false;
        }
        RaktarKeszKisz other = (RaktarKeszKisz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RaktarKeszKisz[ id=" + id + " ]";
    }
    
}
