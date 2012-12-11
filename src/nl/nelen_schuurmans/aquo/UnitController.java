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
public class UnitController extends JpaController {

    private static Logger logger = Logger.getLogger(UnitController.class);

    public static Unit mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        Unit unit = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            UnitDAO dao = new UnitDAO();
            unit = dao.mostRecent();
            tx.commit();
        } catch (RuntimeException ex) {
            logger.error(ex);
            rollback(tx);
            throw ex;
        } finally {
            em.close();
        }
        return unit;
    }

    public static void synchronize(Collection<Unit> units) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            UnitDAO dao = new UnitDAO();
            dao.createOrUpdate(units);
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
