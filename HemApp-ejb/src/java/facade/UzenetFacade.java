/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Beteg;
import entity.Orvos;
import entity.Uzenet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class UzenetFacade extends AbstractFacade<Uzenet> {
    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UzenetFacade() {
        super(Uzenet.class);
    }
    
    public List<Uzenet> getByBeteg(Beteg beteg) {
        return em.createNamedQuery("Uzenet.findByBeteg").setParameter("betegID", beteg).getResultList();
    }
    
    public List<Uzenet> getByBetegAndOrvos(Beteg beteg, Orvos orvos) {
        return em.createNamedQuery("Uzenet.findByBetegAndOrvos").setParameter("betegID", beteg).setParameter("orvosID", orvos).getResultList();
    }
}
