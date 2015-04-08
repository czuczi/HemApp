package controller;

import facade.AdminFacade;
import entity.Admin;
import entity.Beteg;
import entity.Orvos;
import facade.BetegFacade;
import facade.OrvosFacade;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author gczuczor
 */
@Stateless
@ManagedBean(name = "LoginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private AdminFacade adminFacade;
    @EJB
    private BetegFacade betegFacade;
    @EJB
    private OrvosFacade orvosFacade;

    private Admin admin;
    private Beteg beteg;
    private Orvos orvos;

    private String felhNev;
    private String jelszo;
    private boolean invalidated = false;
    private int timeOut = 1200000;

    public void pageRightCheckerForAdmin() {
        if (adminFacade.getByFelhNev(felhNev).isEmpty()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/4dm1n1s7r470r/adminlogin.xhtml");
            } catch (IOException e) {
            }
        }
    }

    public void pageRightCheckerForBeteg() {
        if (betegFacade.getByFelhNev(felhNev).isEmpty()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/beteg/beteglogin.xhtml");
            } catch (IOException e) {
            }
        }
    }

    public void pageRightCheckerForOrvos() {
        if (orvosFacade.getByFelhNev(felhNev).isEmpty()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/orvos/orvoslogin.xhtml");
            } catch (IOException e) {
            }
        }
    }

    public String authenticateForAdmin() {
        if (adminFacade.getByFelhNev(felhNev).isEmpty()) {
            return "/4dm1n1s7r470r/adminlogin?faces-redirect=true";
        }
        admin = adminFacade.getByFelhNev(felhNev).get(0);
        if (admin != null) {
            if (admin.getJelszo().equals(getMD5String(jelszo))) {
                return "/4dm1n1s7r470r/admin?faces-redirect=true";
            } else {
                felhNev = jelszo = null;
            }
        }
        return "/4dm1n1s7r470r/adminlogin?faces-redirect=true";
    }

    public String authenticateForBeteg() {
        if (betegFacade.getByFelhNev(felhNev).isEmpty()) {
            return "/beteg/beteglogin?faces-redirect=true";
        }
        beteg = betegFacade.getByFelhNev(felhNev).get(0);
        if (beteg != null) {
            if (beteg.getJelszo().equals(getMD5String(jelszo))) {
                return "/beteg/beteg?faces-redirect=true";
            } else {
                felhNev = jelszo = null;
            }
        }
        return "/beteg/beteglogin?faces-redirect=true";
    }

    public String authenticateForOrvos() {
        if (orvosFacade.getByFelhNev(felhNev).isEmpty()) {
            return "/orvos/orvoslogin?faces-redirect=true";
        }
        orvos = orvosFacade.getByFelhNev(felhNev).get(0);
        if (orvos != null) {
            if (orvos.getJelszo().equals(getMD5String(jelszo))) {
                return "/orvos/orvos?faces-redirect=true";
            } else {
                felhNev = jelszo = null;
            }
        }
        return "/orvos/orvoslogin?faces-redirect=true";
    }

    public String getMD5String(String password) {
        String pwd = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes(), 0, password.length());
            pwd = new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return pwd;
    }

    public void invalidateSessionContinue() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public void invalidateSessionForAdmin() {
        if (!invalidated) {
            try {
                invalidated = true;
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/4dm1n1s7r470r/adminlogin.xhtml");
            } catch (IOException e) {
            }
        }
    }
    
    public void invalidateSessionForBeteg() {
        if (!invalidated) {
            try {
                invalidated = true;
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/beteg/beteglogin.xhtml");
            } catch (IOException e) {
            }
        }
    }
    
    public void invalidateSessionForOrvos() {
        if (!invalidated) {
            try {
                invalidated = true;
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/orvos/orvoslogin.xhtml");
            } catch (IOException e) {
            }
        }
    }

    public String getFelhNev() {
        return felhNev;
    }

    public void setFelhNev(String felhNev) {
        this.felhNev = felhNev;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public boolean isInvalidated() {
        return invalidated;
    }

    public void setInvalidated(boolean invalidated) {
        this.invalidated = invalidated;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Beteg getBeteg() {
        return beteg;
    }

    public void setBeteg(Beteg beteg) {
        this.beteg = beteg;
    }

    public Orvos getOrvos() {
        return orvos;
    }

    public void setOrvos(Orvos orvos) {
        this.orvos = orvos;
    }

}
