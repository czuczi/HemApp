/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Beteg;
import entity.KeszKisz;
import entity.OtthonKeszKisz;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gczuczor
 */
@Stateless
public class OtthonKeszKiszFacade extends AbstractFacade<OtthonKeszKisz> {
    @PersistenceContext(unitName = "HemApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OtthonKeszKiszFacade() {
        super(OtthonKeszKisz.class);
    }

    public List<OtthonKeszKisz> getByBetegKeszKiszSorozatszam(Beteg beteg, KeszKisz keszKisz, String sorozatszam) {
       return em.createNamedQuery("OtthonKeszKisz.findByBetegKeszKiszSorozat").setParameter("beteg", beteg).setParameter("keszKisz", keszKisz).setParameter("sorozatszam", sorozatszam).getResultList();
    }
    
    public List<OtthonKeszKisz> getByID(String id) {
        return em.createNamedQuery("OtthonKeszKisz.findById").setParameter("id", id).getResultList();
    }
}
