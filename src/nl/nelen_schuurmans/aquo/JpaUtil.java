/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class JpaUtil {
    
    private static final Logger logger = Logger.getLogger(JpaUtil.class);
    private static final EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> threadLocal =
            new ThreadLocal<EntityManager>();
    
    static {
        logger.debug("Create EntityManagerFactory");
        emf = Persistence.createEntityManagerFactory("ddsc");
        logger.debug("Register shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.debug("Close EntityManagerFactory");
                emf.close();
            }
        });
    }
    
    private JpaUtil() {
        super();
    }
    
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public static EntityManager getCurrentEntityManager() {
        EntityManager em = threadLocal.get();
        if (em == null || !em.isOpen()) {
            logger.debug("Create EntityManager");
            em = getEntityManager();
            threadLocal.set(em);
        }
        return em;
    }
}
