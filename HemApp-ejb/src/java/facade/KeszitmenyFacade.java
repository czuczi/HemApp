/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Keszitmeny;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class KeszitmenyFacade extends AbstractFacade<Keszitmeny> {
    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KeszitmenyFacade() {
        super(Keszitmeny.class);
    }
    
    public List<Keszitmeny> getByNev(String nev) {
        return em.createNamedQuery("Keszitmeny.findByNev").setParameter("nev", nev).getResultList();
    }
    
    public List<Keszitmeny> getByID(String id) {
        return em.createNamedQuery("Keszitmeny.findById").setParameter("id", id).getResultList();
    }
}
