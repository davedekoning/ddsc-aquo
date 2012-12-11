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
public class MeasuringDeviceController extends JpaController {

    private static Logger logger = Logger.getLogger(MeasuringDeviceController.class);

    public static MeasuringDevice mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        MeasuringDevice device = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            MeasuringDeviceDAO dao = new MeasuringDeviceDAO();
            device = dao.mostRecent();
            tx.commit();
        } catch (RuntimeException ex) {
            logger.error(ex);
            rollback(tx);
            throw ex;
        } finally {
            em.close();
        }
        return device;
    }

    public static void synchronize(Collection<MeasuringDevice> devices) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            MeasuringDeviceDAO dao = new MeasuringDeviceDAO();
            dao.createOrUpdate(devices);
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
