/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.KeszKisz;
import entity.Orvos;
import entity.RaktarKeszKisz;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class RaktarKeszKiszFacade extends AbstractFacade<RaktarKeszKisz> {
    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RaktarKeszKiszFacade() {
        super(RaktarKeszKisz.class);
    }
    
    public List<RaktarKeszKisz> getByOrvosKeszKiszSorozatszam(Orvos orvos, KeszKisz keszKisz, String sorozatszam) {
        return em.createNamedQuery("RaktarKeszKisz.findByOrvosKeszKiszSorozat").setParameter("orvos", orvos).setParameter("keszKisz", keszKisz).setParameter("sorozatszam", sorozatszam).getResultList();
    }
    
    public List<KeszKisz> getKeszKiszByOrvos(Orvos orvos) {
        return em.createNamedQuery("RaktarKeszKisz.findKeszKiszByOrvos").setParameter("orvos", orvos).getResultList();
    }
}
