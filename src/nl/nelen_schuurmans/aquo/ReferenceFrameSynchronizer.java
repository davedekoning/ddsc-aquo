/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.services.rws.domaintablews._2010._10.DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage;
import nl.services.rws.domaintablews._2010._10.DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage;
import nl.services.rws.domaintablews.contracts._2010._10.Data;
import nl.services.rws.domaintablews.contracts._2010._10.DataField;
import nl.services.rws.domaintablews.contracts._2010._10.DataRow;
import nl.services.rws.domaintablews.contracts._2010._10.StringField;
import org.apache.log4j.Logger;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class ReferenceFrameSynchronizer extends Synchronizer {

    private static final Logger logger = Logger.getLogger(ReferenceFrameSynchronizer.class);
    private static final String TABLE_NAME = "Hoedanigheid";

    public static void main(String[] args) throws Exception {
        new ReferenceFrameSynchronizer().synchronize();
    }

    @Override
    public void synchronize()
            throws DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage,
            DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage {

        ReferenceFrame mostRecent = ReferenceFrameController.mostRecent();
        Data data;

        if (mostRecent == null) {
            // Retrieve all records
            data = getTable(TABLE_NAME, new Date());
        } else {
            // Retrieve new records
            data = getTableChanges(TABLE_NAME, tomorrow(mostRecent.getBeginDate()));
        }

        List<ReferenceFrame> frames = new ArrayList<ReferenceFrame>();

        for (DataRow dataRow : data.getDataRow()) {
            ReferenceFrame frame = new ReferenceFrame();
            frame.setBeginDate(dataRow.getBeginDate());
            frame.setEndDate(dataRow.getEndDate());
            for (DataField dataField : dataRow.getFields().getDataField()) {
                if (dataField.getName().equals("Code")) {
                    StringField stringDataField = (StringField) dataField;
                    frame.setCode(stringDataField.getData());
                } else if (dataField.getName().equals("Omschrijving")) {
                    StringField stringDataField = (StringField) dataField;
                    frame.setDescription(stringDataField.getData());
                } else if (dataField.getName().equals("Groep")) {
                    StringField stringDataField = (StringField) dataField;
                    frame.setGroup(stringDataField.getData());
                } else {
                    logger.warn("Unknown field: " + dataField.getName());
                }
            }
            frames.add(frame);
        }

        ReferenceFrameController.synchronize(frames);

    }
}
