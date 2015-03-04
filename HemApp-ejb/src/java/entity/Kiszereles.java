/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "kiszereles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kiszereles.findAll", query = "SELECT k FROM Kiszereles k"),
    @NamedQuery(name = "Kiszereles.findById", query = "SELECT k FROM Kiszereles k WHERE k.id = :id"),
    @NamedQuery(name = "Kiszereles.findByNe", query = "SELECT k FROM Kiszereles k WHERE k.ne = :ne")})
public class Kiszereles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NE")
    private int ne;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kiszerelesID")
    private Collection<KeszKisz> keszKiszCollection;

    public Kiszereles() {
    }

    public Kiszereles(String id) {
        this.id = id;
    }

    public Kiszereles(String id, int ne) {
        this.id = id;
        this.ne = ne;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNe() {
        return ne;
    }

    public void setNe(int ne) {
        this.ne = ne;
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
        if (!(object instanceof Kiszereles)) {
            return false;
        }
        Kiszereles other = (Kiszereles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Kiszereles[ id=" + id + " ]";
    }
    
}
