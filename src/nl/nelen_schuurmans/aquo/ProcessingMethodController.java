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
public class ProcessingMethodController extends JpaController {

    private static Logger logger = Logger.getLogger(ProcessingMethodController.class);

    public static ProcessingMethod mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        ProcessingMethod method = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            ProcessingMethodDAO dao = new ProcessingMethodDAO();
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

    public static void synchronize(Collection<ProcessingMethod> methods) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            ProcessingMethodDAO dao = new ProcessingMethodDAO();
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
