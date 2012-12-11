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
public class MeasuringDeviceDAO implements GenericDAO<MeasuringDevice> {

    private static final Logger logger = Logger.getLogger(MeasuringDeviceDAO.class);

    @Override
    public void createOrUpdate(Collection<MeasuringDevice> devices) {
        for (MeasuringDevice device : devices) {
            createOrUpdate(device);
        }
    }

    @Override
    public void createOrUpdate(MeasuringDevice device) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        MeasuringDevice device2 = findByCode(device.getCode());
        if (device2 == null) {
            logger.debug("Creating " + device);
            em.persist(device);
        } else {
            logger.debug("Updating " + device);
            device2.update(device);
        }
    }

    @Override
    public MeasuringDevice findByCode(String code) {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<MeasuringDevice> query =
                em.createNamedQuery("MeasuringDevice.findByCode", MeasuringDevice.class);
        try {
            return query.setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public MeasuringDevice mostRecent() {
        EntityManager em = JpaUtil.getCurrentEntityManager();
        TypedQuery<MeasuringDevice> query =
                em.createNamedQuery("MeasuringDevice.mostRecent", MeasuringDevice.class);
        try {
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
