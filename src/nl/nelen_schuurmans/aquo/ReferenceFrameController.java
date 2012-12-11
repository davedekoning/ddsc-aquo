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
public class ReferenceFrameController extends JpaController {

    private static Logger logger = Logger.getLogger(ReferenceFrameController.class);

    public static ReferenceFrame mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        ReferenceFrame frame = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            ReferenceFrameDAO dao = new ReferenceFrameDAO();
            frame = dao.mostRecent();
            tx.commit();
        } catch (RuntimeException ex) {
            logger.error(ex);
            rollback(tx);
            throw ex;
        } finally {
            em.close();
        }
        return frame;
    }

    public static void synchronize(Collection<ReferenceFrame> frames) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            ReferenceFrameDAO dao = new ReferenceFrameDAO();
            dao.createOrUpdate(frames);
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
