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
public class ProcessingMethodDAO implements GenericDAO<ProcessingMethod> {

    private static final Logger logger = Logger.getLogger(ProcessingMethodDAO.class);

    @Override
    public void createOrUpdate(Collection<ProcessingMethod> methods) {
        for (ProcessingMethod method : methods) {
            createOrUpdate(method);
        }
    }

    @Override
    public void createOrUpdate(ProcessingMethod method) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        ProcessingMethod method2 = findByCode(method.getCode());
        if (method2 == null) {
            logger.debug("Creating " + method);
            em.persist(method);
        } else {
            logger.debug("Updating " + method);
            method2.update(method);
        }
    }

    @Override
    public ProcessingMethod findByCode(String code) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<ProcessingMethod> query =
                em.createNamedQuery("ProcessingMethod.findByCode", ProcessingMethod.class);
        try {
            return query.setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public ProcessingMethod mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<ProcessingMethod> query =
                em.createNamedQuery("ProcessingMethod.mostRecent", ProcessingMethod.class);
        try {
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
