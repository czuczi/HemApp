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
@Table(name = "beteg")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Beteg.findAll", query = "SELECT b FROM Beteg b"),
    @NamedQuery(name = "Beteg.findById", query = "SELECT b FROM Beteg b WHERE b.id = :id"),
    @NamedQuery(name = "Beteg.findByFelhnev", query = "SELECT b FROM Beteg b WHERE b.felhnev = :felhnev"),
    @NamedQuery(name = "Beteg.findByJelszo", query = "SELECT b FROM Beteg b WHERE b.jelszo = :jelszo"),
    @NamedQuery(name = "Beteg.findByVezeteknev", query = "SELECT b FROM Beteg b WHERE b.vezeteknev = :vezeteknev"),
    @NamedQuery(name = "Beteg.findByKeresztnev", query = "SELECT b FROM Beteg b WHERE b.keresztnev = :keresztnev"),
    @NamedQuery(name = "Beteg.findByTaj", query = "SELECT b FROM Beteg b WHERE b.taj = :taj"),
    @NamedQuery(name = "Beteg.findByEmail", query = "SELECT b FROM Beteg b WHERE b.email = :email"),
    @NamedQuery(name = "Beteg.findByTelefon", query = "SELECT b FROM Beteg b WHERE b.telefon = :telefon")})
public class Beteg implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAJ")
    private int taj;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 150)
    @Column(name = "Email")
    private String email;
    @Size(max = 20)
    @Column(name = "Telefon")
    private String telefon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betegID")
    private Collection<Uzenet> uzenetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betegID")
    private Collection<Injekciotortenet> injekciotortenetCollection;
    @JoinColumn(name = "Orvos_ID", referencedColumnName = "ID")
    @ManyToOne
    private Orvos orvosID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betegID")
    private Collection<OtthonKeszKisz> otthonKeszKiszCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betegID")
    private Collection<BeadottKeszKisz> beadottKeszKiszCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betegID")
    private Collection<KertKeszKisz> kertKeszKiszCollection;

    public Beteg() {
    }

    public Beteg(String felhnev, String jelszo, String vezeteknev, String keresztnev, int taj) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.felhnev = felhnev;
        this.jelszo = jelszo;
        this.vezeteknev = vezeteknev;
        this.keresztnev = keresztnev;
        this.taj = taj;
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

    public int getTaj() {
        return taj;
    }

    public void setTaj(int taj) {
        this.taj = taj;
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
    public Collection<Injekciotortenet> getInjekciotortenetCollection() {
        return injekciotortenetCollection;
    }

    public void setInjekciotortenetCollection(Collection<Injekciotortenet> injekciotortenetCollection) {
        this.injekciotortenetCollection = injekciotortenetCollection;
    }

    public Orvos getOrvosID() {
        return orvosID;
    }

    public void setOrvosID(Orvos orvosID) {
        this.orvosID = orvosID;
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
        if (!(object instanceof Beteg)) {
            return false;
        }
        Beteg other = (Beteg) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return vezeteknev + " " + keresztnev + " " + String.format("%09d", taj);
    }
    
}
