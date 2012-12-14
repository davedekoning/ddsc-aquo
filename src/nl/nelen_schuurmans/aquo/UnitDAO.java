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
public class UnitDAO implements GenericDAO<Unit> {

    private static final Logger logger = Logger.getLogger(UnitDAO.class);

    @Override
    public void createOrUpdate(Collection<Unit> units) {
        for (Unit unit : units) {
            createOrUpdate(unit);
        }
    }

    @Override
    public void createOrUpdate(Unit unit) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        Unit unit2 = findByCode(unit.getCode());
        if (unit2 == null) {
            logger.debug("Creating " + unit);
            em.persist(unit);
        } else {
            logger.debug("Updating " + unit);
            unit2.update(unit);
        }
    }

    @Override
    public Unit findByCode(String code) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<Unit> query =
                em.createNamedQuery("Unit.findByCode", Unit.class);
        try {
            return query.setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Unit mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<Unit> query =
                em.createNamedQuery("Unit.mostRecent", Unit.class);
        try {
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
