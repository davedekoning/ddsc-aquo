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
public class ParameterController extends JpaController {

    private static Logger logger = Logger.getLogger(ParameterController.class);

    public static Parameter mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        Parameter parameter = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            ParameterDAO dao = new ParameterDAO();
            parameter = dao.mostRecent();
            tx.commit();
        } catch (RuntimeException ex) {
            logger.error(ex);
            rollback(tx);
            throw ex;
        } finally {
            em.close();
        }
        return parameter;
    }

    public static void synchronize(Collection<Parameter> parameters) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            ParameterDAO dao = new ParameterDAO();
            dao.createOrUpdate(parameters);
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
