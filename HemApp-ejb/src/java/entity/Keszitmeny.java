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
@Table(name = "keszitmeny")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Keszitmeny.findAll", query = "SELECT k FROM Keszitmeny k"),
    @NamedQuery(name = "Keszitmeny.findById", query = "SELECT k FROM Keszitmeny k WHERE k.id = :id"),
    @NamedQuery(name = "Keszitmeny.findByNev", query = "SELECT k FROM Keszitmeny k WHERE k.nev = :nev")})
public class Keszitmeny implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nev")
    private String nev;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keszitmenyID")
    private Collection<Injekciotortenet> injekciotortenetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keszitmenyID")
    private Collection<KeszKisz> keszKiszCollection;

    public Keszitmeny() {
    }

    public Keszitmeny(String nev) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.nev = nev;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    @XmlTransient
    public Collection<Injekciotortenet> getInjekciotortenetCollection() {
        return injekciotortenetCollection;
    }

    public void setInjekciotortenetCollection(Collection<Injekciotortenet> injekciotortenetCollection) {
        this.injekciotortenetCollection = injekciotortenetCollection;
    }

    @XmlTransient
    public Collection<KeszKisz> getKeszKiszCollection() {
        return keszKiszCollection;
    }

    public void setKeszKiszCollection(Collection<KeszKisz> keszKiszCollection) {
        this.keszKiszCollection = keszKiszCollection;
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
        if (!(object instanceof Keszitmeny)) {
            return false;
        }
        Keszitmeny other = (Keszitmeny) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Keszitmeny[ id=" + id + " ]";
    }
    
}
