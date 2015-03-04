/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "uzenet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uzenet.findAll", query = "SELECT u FROM Uzenet u"),
    @NamedQuery(name = "Uzenet.findById", query = "SELECT u FROM Uzenet u WHERE u.id = :id"),
    @NamedQuery(name = "Uzenet.findByIdopont", query = "SELECT u FROM Uzenet u WHERE u.idopont = :idopont"),
    @NamedQuery(name = "Uzenet.findBySzoveg", query = "SELECT u FROM Uzenet u WHERE u.szoveg = :szoveg"),
    @NamedQuery(name = "Uzenet.findByKepLink", query = "SELECT u FROM Uzenet u WHERE u.kepLink = :kepLink")})
public class Uzenet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idopont")
    @Temporal(TemporalType.TIMESTAMP)
    private Date idopont;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "szoveg")
    private String szoveg;
    @Size(max = 300)
    @Column(name = "kep_link")
    private String kepLink;
    @JoinColumn(name = "Beteg_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Beteg betegID;
    @JoinColumn(name = "Orvos_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Orvos orvosID;

    public Uzenet() {
    }

    public Uzenet(String id) {
        this.id = id;
    }

    public Uzenet(String id, Date idopont, String szoveg) {
        this.id = id;
        this.idopont = idopont;
        this.szoveg = szoveg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getIdopont() {
        return idopont;
    }

    public void setIdopont(Date idopont) {
        this.idopont = idopont;
    }

    public String getSzoveg() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg = szoveg;
    }

    public String getKepLink() {
        return kepLink;
    }

    public void setKepLink(String kepLink) {
        this.kepLink = kepLink;
    }

    public Beteg getBetegID() {
        return betegID;
    }

    public void setBetegID(Beteg betegID) {
        this.betegID = betegID;
    }

    public Orvos getOrvosID() {
        return orvosID;
    }

    public void setOrvosID(Orvos orvosID) {
        this.orvosID = orvosID;
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
        if (!(object instanceof Uzenet)) {
            return false;
        }
        Uzenet other = (Uzenet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Uzenet[ id=" + id + " ]";
    }
    
}
