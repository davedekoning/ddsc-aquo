/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class DateAdapter {

    public static Date parseDate(String date) {
        return DatatypeConverter.parseDate(date).getTime();
    }

    public static String printDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return DatatypeConverter.printDate(calendar);
    }
}
