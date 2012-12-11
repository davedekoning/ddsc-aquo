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
public class ParameterDAO implements GenericDAO<Parameter> {
    
    private static final Logger logger = Logger.getLogger(ParameterDAO.class);
    
    @Override
    public void createOrUpdate(Collection<Parameter> parameters) {
        for (Parameter parameter : parameters) {
            createOrUpdate(parameter);
        }
    }
    
    @Override
    public void createOrUpdate(Parameter parameter) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        Parameter parameter2 = findByCode(parameter.getCode());
        if (parameter2 == null) {
            logger.debug("Creating " + parameter);
            em.persist(parameter);
        } else {
            logger.debug("Updating " + parameter);
            parameter2.update(parameter);
        }
    }
    
    @Override
    public Parameter findByCode(String code) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<Parameter> query =
                em.createNamedQuery("Parameter.findByCode", Parameter.class);
        try {
            return query.setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public Parameter mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<Parameter> query =
                em.createNamedQuery("Parameter.mostRecent", Parameter.class);
        try {
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
