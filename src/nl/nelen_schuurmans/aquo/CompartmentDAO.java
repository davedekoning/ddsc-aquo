/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class CompartmentDAO implements GenericDAO<Compartment> {

    private static final Logger logger = Logger.getLogger(CompartmentDAO.class);

    @Override
    public void createOrUpdate(Collection<Compartment> compartments) {
        for (Compartment compartment : compartments) {
            createOrUpdate(compartment);
        }
    }

    @Override
    public void createOrUpdate(Compartment compartment) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        Compartment compartment2 = findByCode(compartment.getCode());
        if (compartment2 == null) {
            logger.debug("Creating " + compartment);
            em.persist(compartment);
        } else {
            logger.debug("Updating " + compartment);
            compartment2.update(compartment);
        }
    }

    @Override
    public Compartment findByCode(String code) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<Compartment> query =
                em.createNamedQuery("Compartment.findByCode", Compartment.class);
        try {
            return query.setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Compartment mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<Compartment> query =
                em.createNamedQuery("Compartment.mostRecent", Compartment.class);
        try {
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
