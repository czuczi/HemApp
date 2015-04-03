/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Orvos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class OrvosFacade extends AbstractFacade<Orvos> {

    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrvosFacade() {
        super(Orvos.class);
    }

    public List<Orvos> getByID(String id) {
        return em.createNamedQuery("Orvos.findById").setParameter("id", id).getResultList();
    }

    public List<Orvos> getByFelhNev(String felhNev) {
        return em.createNamedQuery("Orvos.findByFelhnev").setParameter("felhnev", felhNev).getResultList();
    }
}
