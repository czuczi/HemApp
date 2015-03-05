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
@Table(name = "admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
    @NamedQuery(name = "Admin.findByFelhnev", query = "SELECT a FROM Admin a WHERE a.felhnev = :felhnev"),
    @NamedQuery(name = "Admin.findByJelszo", query = "SELECT a FROM Admin a WHERE a.jelszo = :jelszo")})
public class Admin implements Serializable {
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
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Felh_nev")
    private String felhnev;
    @Size(max = 32)
    @Column(name = "Jelszo")
    private String jelszo;

    public Admin() {
    }

    public Admin(String felhnev, String jelszo, String vezeteknev, String keresztnev) {
        this.felhnev = felhnev;
        this.jelszo = jelszo;
        this.vezeteknev = vezeteknev;
        this.keresztnev = keresztnev;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (felhnev != null ? felhnev.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        if ((this.felhnev == null && other.felhnev != null) || (this.felhnev != null && !this.felhnev.equals(other.felhnev))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Admin[ felhnev=" + felhnev + " ]";
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
    
}
