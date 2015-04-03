/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Beteg;
import entity.Injekciotortenet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class InjekciotortenetFacade extends AbstractFacade<Injekciotortenet> {
    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InjekciotortenetFacade() {
        super(Injekciotortenet.class);
    }
    
    public List<Injekciotortenet> getActualByBeteg(Beteg beteg){
        return em.createNamedQuery("Injekciotortenet.findActualByBeteg").setParameter("betegID", beteg).getResultList();
    }
}
