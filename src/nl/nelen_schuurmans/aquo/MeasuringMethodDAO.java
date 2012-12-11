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
public class MeasuringMethodDAO implements GenericDAO<MeasuringMethod> {

    private static final Logger logger = Logger.getLogger(MeasuringMethodDAO.class);

    @Override
    public void createOrUpdate(Collection<MeasuringMethod> methods) {
        for (MeasuringMethod method : methods) {
            createOrUpdate(method);
        }
    }

    @Override
    public void createOrUpdate(MeasuringMethod method) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        MeasuringMethod method2 = findByCode(method.getCode());
        if (method2 == null) {
            logger.debug("Creating " + method);
            em.persist(method);
        } else {
            logger.debug("Updating " + method);
            method2.update(method);
        }
    }

    @Override
    public MeasuringMethod findByCode(String code) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<MeasuringMethod> query =
                em.createNamedQuery("MeasuringMethod.findByCode", MeasuringMethod.class);
        try {
            return query.setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public MeasuringMethod mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<MeasuringMethod> query =
                em.createNamedQuery("MeasuringMethod.mostRecent", MeasuringMethod.class);
        try {
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
