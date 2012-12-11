/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.apache.log4j.Logger;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class MeasuringMethodController extends JpaController {

    private static Logger logger = Logger.getLogger(MeasuringMethodController.class);

    public static MeasuringMethod mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        MeasuringMethod method = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            MeasuringMethodDAO dao = new MeasuringMethodDAO();
            method = dao.mostRecent();
            tx.commit();
        } catch (RuntimeException ex) {
            logger.error(ex);
            rollback(tx);
            throw ex;
        } finally {
            em.close();
        }
        return method;
    }

    public static void synchronize(Collection<MeasuringMethod> methods) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            MeasuringMethodDAO dao = new MeasuringMethodDAO();
            dao.createOrUpdate(methods);
            tx.commit();
        } catch (RuntimeException ex) {
            logger.error(ex);
            rollback(tx);
            throw ex;
        } finally {
            em.close();
        }
    }
}
