/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gczuczor
 */
@Entity
@Table(name = "kesz_kisz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KeszKisz.findAll", query = "SELECT k FROM KeszKisz k"),
    @NamedQuery(name = "KeszKisz.findById", query = "SELECT k FROM KeszKisz k WHERE k.id = :id")})
public class KeszKisz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keszKiszID")
    private Collection<RaktarKeszKisz> raktarKeszKiszCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keszKiszID")
    private Collection<OtthonKeszKisz> otthonKeszKiszCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keszKiszID")
    private Collection<BeadottKeszKisz> beadottKeszKiszCollection;
    @JoinColumn(name = "Keszitmeny_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Keszitmeny keszitmenyID;
    @JoinColumn(name = "Kiszereles_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Kiszereles kiszerelesID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keszKiszID")
    private Collection<KertKeszKisz> kertKeszKiszCollection;

    public KeszKisz() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<RaktarKeszKisz> getRaktarKeszKiszCollection() {
        return raktarKeszKiszCollection;
    }

    public void setRaktarKeszKiszCollection(Collection<RaktarKeszKisz> raktarKeszKiszCollection) {
        this.raktarKeszKiszCollection = raktarKeszKiszCollection;
    }

    @XmlTransient
    public Collection<OtthonKeszKisz> getOtthonKeszKiszCollection() {
        return otthonKeszKiszCollection;
    }

    public void setOtthonKeszKiszCollection(Collection<OtthonKeszKisz> otthonKeszKiszCollection) {
        this.otthonKeszKiszCollection = otthonKeszKiszCollection;
    }

    @XmlTransient
    public Collection<BeadottKeszKisz> getBeadottKeszKiszCollection() {
        return beadottKeszKiszCollection;
    }

    public void setBeadottKeszKiszCollection(Collection<BeadottKeszKisz> beadottKeszKiszCollection) {
        this.beadottKeszKiszCollection = beadottKeszKiszCollection;
    }

    public Keszitmeny getKeszitmenyID() {
        return keszitmenyID;
    }

    public void setKeszitmenyID(Keszitmeny keszitmenyID) {
        this.keszitmenyID = keszitmenyID;
    }

    public Kiszereles getKiszerelesID() {
        return kiszerelesID;
    }

    public void setKiszerelesID(Kiszereles kiszerelesID) {
        this.kiszerelesID = kiszerelesID;
    }

    @XmlTransient
    public Collection<KertKeszKisz> getKertKeszKiszCollection() {
        return kertKeszKiszCollection;
    }

    public void setKertKeszKiszCollection(Collection<KertKeszKisz> kertKeszKiszCollection) {
        this.kertKeszKiszCollection = kertKeszKiszCollection;
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
        if (!(object instanceof KeszKisz)) {
            return false;
        }
        KeszKisz other = (KeszKisz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KeszKisz[ id=" + id + " ]";
    }
    
}
