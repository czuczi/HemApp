/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gczuczor
 */
@Entity
@Table(name = "otthon_kesz_kisz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtthonKeszKisz.findAll", query = "SELECT o FROM OtthonKeszKisz o"),
    @NamedQuery(name = "OtthonKeszKisz.findById", query = "SELECT o FROM OtthonKeszKisz o WHERE o.id = :id"),
    @NamedQuery(name = "OtthonKeszKisz.findBySorozatszam", query = "SELECT o FROM OtthonKeszKisz o WHERE o.sorozatszam = :sorozatszam"),
    @NamedQuery(name = "OtthonKeszKisz.findByBetegKeszKiszSorozat", query = "SELECT o FROM OtthonKeszKisz o WHERE o.keszKiszID = :keszKisz AND o.betegID = :beteg AND o.sorozatszam = :sorozatszam"),
    @NamedQuery(name = "OtthonKeszKisz.findByDarab", query = "SELECT o FROM OtthonKeszKisz o WHERE o.darab = :darab")})
public class OtthonKeszKisz implements Serializable {
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
    @Column(name = "sorozatszam")
    private String sorozatszam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "darab")
    private int darab;
    @JoinColumn(name = "Beteg_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Beteg betegID;
    @JoinColumn(name = "Kesz_Kisz_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private KeszKisz keszKiszID;

    public OtthonKeszKisz() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public OtthonKeszKisz(String sorozatszam, int darab) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
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
        if (!(object instanceof OtthonKeszKisz)) {
            return false;
        }
        OtthonKeszKisz other = (OtthonKeszKisz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OtthonKeszKisz[ id=" + id + " ]";
    }
    
}
