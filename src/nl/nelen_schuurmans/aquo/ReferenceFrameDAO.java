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
public class ReferenceFrameDAO implements GenericDAO<ReferenceFrame> {

    private static final Logger logger = Logger.getLogger(ReferenceFrameDAO.class);

    @Override
    public void createOrUpdate(Collection<ReferenceFrame> frames) {
        for (ReferenceFrame frame : frames) {
            createOrUpdate(frame);
        }
    }

    @Override
    public void createOrUpdate(ReferenceFrame frame) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        ReferenceFrame frame2 = findByCode(frame.getCode());
        if (frame2 == null) {
            logger.debug("Creating " + frame);
            em.persist(frame);
        } else {
            logger.debug("Updating " + frame);
            frame2.update(frame);
        }
    }

    @Override
    public ReferenceFrame findByCode(String code) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<ReferenceFrame> query =
                em.createNamedQuery("ReferenceFrame.findByCode", ReferenceFrame.class);
        try {
            return query.setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public ReferenceFrame mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<ReferenceFrame> query =
                em.createNamedQuery("ReferenceFrame.mostRecent", ReferenceFrame.class);
        try {
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
