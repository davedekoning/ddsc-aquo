/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class Props {

    public static Map getSystemProperties(String regex) {

        @SuppressWarnings({"unchecked", "rawtypes"})
        Map<String, String> map = new HashMap(System.getProperties());
        if (regex != null) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (!key.matches(regex)) {
                    it.remove();
                }
            }
        }
        return map;
    }
}
