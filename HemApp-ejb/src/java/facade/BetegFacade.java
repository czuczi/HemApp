/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Beteg;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class BetegFacade extends AbstractFacade<Beteg> {

    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BetegFacade() {
        super(Beteg.class);
    }

    public List<Beteg> getByFelhNev(String felhNev) {
        return em.createNamedQuery("Beteg.findByFelhnev").setParameter("felhnev", felhNev).getResultList();
    }

    public List<Beteg> getByID(String id) {
        return em.createNamedQuery("Beteg.findById").setParameter("id", id).getResultList();
    }
}
