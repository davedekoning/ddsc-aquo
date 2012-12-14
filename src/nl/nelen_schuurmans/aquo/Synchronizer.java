/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import nl.services.rws.domaintablews._2010._10.DomainTableService;
import nl.services.rws.domaintablews._2010._10.DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage;
import nl.services.rws.domaintablews._2010._10.DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage;
import nl.services.rws.domaintablews._2010._10.DomainTableWS;
import nl.services.rws.domaintablews.contracts._2010._10.Data;
import nl.services.rws.domaintablews.contracts._2010._10.DataRow;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableChangesRequest;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableChangesResponse;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableRequest;
import nl.services.rws.domaintablews.contracts._2010._10.GetDomainTableResponse;
import org.apache.log4j.Logger;

/**
 * Abstract base class for Aquo DS table synchronizers.
 *
 * Large tables cannot be retrieved with a single request: the Aquo DS web
 * service will throw an error if the number of rows exceeds a certain maximum.
 * For that reason, we'll make several smaller requests.
 *
 * TODO: to protect our own resources, it's arguably better to use a similar
 * strategy instead of storing all rows in memory before persisting them.
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public abstract class Synchronizer {

    private static final Logger logger = Logger.getLogger(Synchronizer.class);
    private static final int PAGE_SIZE = 100; // Any recommended value?

    public static Data getTable(String table, Date date)
            throws DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage {

        GetDomainTableRequest request = new GetDomainTableRequest();
        request.setDomaintableName(table);
        request.setCheckDate(date);
        request.setPageSize(PAGE_SIZE);

        DomainTableWS service = new DomainTableWS();
        DomainTableService port = service.getBasic();

        Data data = new Data();
        List<DataRow> page;
        int startPage = 0;

        do {
            request.setStartPage(startPage * PAGE_SIZE);
            GetDomainTableResponse response = port.getDomainTable(request);
            page = response.getData().getDataRow();
            String fmt = "Got page %d (%d rows)";
            logger.debug(String.format(fmt, startPage, page.size()));
            data.getDataRow().addAll(page);
            startPage += 1;
        } while (!page.isEmpty());
        String fmt = "Got %d rows in total)";
        logger.debug(String.format(fmt, data.getDataRow().size()));

        return data;
    }

    public static Data getTableChanges(String table, Date date)
            throws DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage {

        GetDomainTableChangesRequest request = new GetDomainTableChangesRequest();
        request.setDomaintableName(table);
        request.setCheckDate(date);
        request.setPageSize(PAGE_SIZE);

        DomainTableWS service = new DomainTableWS();
        DomainTableService port = service.getBasic();

        Data data = new Data();
        List<DataRow> page;
        int startPage = 0;

        do {
            request.setStartPage(startPage * PAGE_SIZE);
            GetDomainTableChangesResponse response = port.getDomainTableChanges(request);
            page = response.getData().getDataRow();
            String fmt = "Got page %d (%d rows)";
            logger.debug(String.format(fmt, startPage, page.size()));
            data.getDataRow().addAll(page);
            startPage += 1;
        } while (!page.isEmpty());
        String fmt = "Got %d rows in total";
        logger.debug(String.format(fmt, data.getDataRow().size()));

        return data;
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
