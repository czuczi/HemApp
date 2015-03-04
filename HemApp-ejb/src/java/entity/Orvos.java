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
@Table(name = "orvos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orvos.findAll", query = "SELECT o FROM Orvos o"),
    @NamedQuery(name = "Orvos.findById", query = "SELECT o FROM Orvos o WHERE o.id = :id"),
    @NamedQuery(name = "Orvos.findByFelhnev", query = "SELECT o FROM Orvos o WHERE o.felhnev = :felhnev"),
    @NamedQuery(name = "Orvos.findByJelszo", query = "SELECT o FROM Orvos o WHERE o.jelszo = :jelszo"),
    @NamedQuery(name = "Orvos.findByVezeteknev", query = "SELECT o FROM Orvos o WHERE o.vezeteknev = :vezeteknev"),
    @NamedQuery(name = "Orvos.findByKeresztnev", query = "SELECT o FROM Orvos o WHERE o.keresztnev = :keresztnev"),
    @NamedQuery(name = "Orvos.findByEmail", query = "SELECT o FROM Orvos o WHERE o.email = :email"),
    @NamedQuery(name = "Orvos.findByTelefon", query = "SELECT o FROM Orvos o WHERE o.telefon = :telefon")})
public class Orvos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Felh_nev")
    private String felhnev;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "Jelszo")
    private String jelszo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Vezeteknev")
    private String vezeteknev;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Keresztnev")
    private String keresztnev;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 150)
    @Column(name = "Email")
    private String email;
    @Size(max = 20)
    @Column(name = "Telefon")
    private String telefon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orvosID")
    private Collection<Uzenet> uzenetCollection;
    @OneToMany(mappedBy = "orvosID")
    private Collection<Beteg> betegCollection;

    public Orvos() {
    }

    public Orvos(String id) {
        this.id = id;
    }

    public Orvos(String id, String felhnev, String jelszo, String vezeteknev, String keresztnev) {
        this.id = id;
        this.felhnev = felhnev;
        this.jelszo = jelszo;
        this.vezeteknev = vezeteknev;
        this.keresztnev = keresztnev;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFelhnev() {
        return felhnev;
    }

    public void setFelhnev(String felhnev) {
        this.felhnev = felhnev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getVezeteknev() {
        return vezeteknev;
    }

    public void setVezeteknev(String vezeteknev) {
        this.vezeteknev = vezeteknev;
    }

    public String getKeresztnev() {
        return keresztnev;
    }

    public void setKeresztnev(String keresztnev) {
        this.keresztnev = keresztnev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @XmlTransient
    public Collection<Uzenet> getUzenetCollection() {
        return uzenetCollection;
    }

    public void setUzenetCollection(Collection<Uzenet> uzenetCollection) {
        this.uzenetCollection = uzenetCollection;
    }

    @XmlTransient
    public Collection<Beteg> getBetegCollection() {
        return betegCollection;
    }

    public void setBetegCollection(Collection<Beteg> betegCollection) {
        this.betegCollection = betegCollection;
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
        if (!(object instanceof Orvos)) {
            return false;
        }
        Orvos other = (Orvos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Orvos[ id=" + id + " ]";
    }
    
}
