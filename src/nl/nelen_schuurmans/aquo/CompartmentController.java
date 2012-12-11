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
public class CompartmentController extends JpaController {

    private static Logger logger = Logger.getLogger(CompartmentController.class);

    public static Compartment mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        Compartment compartment = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            CompartmentDAO dao = new CompartmentDAO();
            compartment = dao.mostRecent();
            tx.commit();
        } catch (RuntimeException ex) {
            logger.error(ex);
            rollback(tx);
            throw ex;
        } finally {
            em.close();
        }
        return compartment;
    }

    public static void synchronize(Collection<Compartment> compartments) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            CompartmentDAO dao = new CompartmentDAO();
            dao.createOrUpdate(compartments);
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
