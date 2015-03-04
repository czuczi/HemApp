package controller;

import facade.AdminFacade;
import entity.Admin;
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

    private String felhNev;
    private String jelszo;
    private boolean invalidated = false;
    private int timeOut = 1200000;

    public void pageRightChecker() {
        if (jelszo == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/careermap.xhtml");
            } catch (IOException e) {
            }
        }
    }

    public String authenticate() {
        if (adminFacade.getByFelhNev(felhNev).isEmpty()) {
            return "/4dm1n1s7r470r/adminlogin?faces-redirect=true";
        }
        Admin admin = adminFacade.getByFelhNev(felhNev).get(0);
        if (admin != null) {
            if (admin.getJelszo().equals(getMD5String(jelszo))) {
                return "/4dm1n1s7r470r/admin?faces-redirect=true";
            } else {
                felhNev = jelszo = null;
            }
        }
        return "/4dm1n1s7r470r/adminlogin?faces-redirect=true";
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

    public void invalidateSession() {
        if (!invalidated) {
            try {
                invalidated = true;
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/4dm1n1s7r470r/adminlogin.xhtml");
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

}
