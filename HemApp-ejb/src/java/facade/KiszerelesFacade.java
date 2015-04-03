/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Kiszereles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class KiszerelesFacade extends AbstractFacade<Kiszereles> {
    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KiszerelesFacade() {
        super(Kiszereles.class);
    }
    
    public List<Kiszereles> getByID(String id) {
        return em.createNamedQuery("Kiszereles.findById").setParameter("id", id).getResultList();
    }
}
