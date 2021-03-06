/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.KeszKisz;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class KeszKiszFacade extends AbstractFacade<KeszKisz> {
    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KeszKiszFacade() {
        super(KeszKisz.class);
    }
    
    public List<KeszKisz> getByID(String id) {
        return em.createNamedQuery("KeszKisz.findById").setParameter("id", id).getResultList();
    }
}
