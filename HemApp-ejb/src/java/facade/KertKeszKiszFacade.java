/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Beteg;
import entity.KertKeszKisz;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class KertKeszKiszFacade extends AbstractFacade<KertKeszKisz> {

    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KertKeszKiszFacade() {
        super(KertKeszKisz.class);
    }

    public List<KertKeszKisz> getActualByBeteg(Beteg beteg) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date sevenDaysAgo = cal.getTime();
        return em.createNamedQuery("KertKeszKisz.findActualByBeteg").setParameter("startDate", sevenDaysAgo).setParameter("beteg", beteg).getResultList();
    }
    
    public List<KertKeszKisz> getActual() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date sevenDaysAgo = cal.getTime();
        return em.createNamedQuery("KertKeszKisz.findActual").setParameter("startDate", sevenDaysAgo).getResultList();
    }
}
