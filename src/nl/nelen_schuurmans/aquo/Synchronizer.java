/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.Calendar;
import java.util.Date;
import nl.services.rws.domaintablews._2010._10.DomainTableService;
import nl.services.rws.domaintablews._2010._10.DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage;
import nl.services.rws.domaintablews._2010._10.DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage;
import nl.services.rws.domaintablews._2010._10.DomainTableWS;
import nl.services.rws.domaintablews.contracts._2010._10.Data;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableChangesRequest;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableChangesResponse;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableRequest;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public abstract class Synchronizer {

    private static final Logger logger = Logger.getLogger(Synchronizer.class);

    public static Data getTable(String table, Date date)
            throws DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage {
        GetDomainTableRequest request = new GetDomainTableRequest();
        request.setDomaintableName(table);
        request.setCheckDate(date);
        DomainTableWS service = new DomainTableWS();
        DomainTableService port = service.getBasic();
        GetDomainTableResponse response = port.getDomainTable(request);
        return response.getData();
    }

    public static Data getTableChanges(String table, Date date)
            throws DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage {
        GetDomainTableChangesRequest request = new GetDomainTableChangesRequest();
        request.setDomaintableName(table);
        request.setCheckDate(date);
        DomainTableWS service = new DomainTableWS();
        DomainTableService port = service.getBasic();
        GetDomainTableChangesResponse response = port.getDomainTableChanges(request);
        return response.getData();
    }

    public static Date tomorrow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public abstract void synchronize()
            throws DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage,
            DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage;
}
