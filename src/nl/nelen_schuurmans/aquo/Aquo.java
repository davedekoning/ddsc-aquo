/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import org.apache.log4j.Logger;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class Aquo {

    private static final Logger logger = Logger.getLogger(Aquo.class);
    private static String[] classNames = {
        "nl.nelen_schuurmans.aquo.CompartmentSynchronizer",
        "nl.nelen_schuurmans.aquo.MeasuringDeviceSynchronizer",
        "nl.nelen_schuurmans.aquo.MeasuringMethodSynchronizer",
        "nl.nelen_schuurmans.aquo.ParameterSynchronizer",
        "nl.nelen_schuurmans.aquo.ProcessingMethodSynchronizer",
        "nl.nelen_schuurmans.aquo.ReferenceFrameSynchronizer",
        "nl.nelen_schuurmans.aquo.UnitSynchronizer"
    };

    public static void main(String[] args) {
        for (String className : classNames) {
            try {
                Object object = Class.forName(className).newInstance();
                Synchronizer synchronizer = (Synchronizer) object;
                synchronizer.synchronize();
            } catch (Exception ex) {
                logger.error(className, ex);
            }
        }
    }
}
