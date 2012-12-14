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
import nl.services.rws.domaintablews.contracts._2010._10.IntegerField;
import nl.services.rws.domaintablews.contracts._2010._10.StringField;
import org.apache.log4j.Logger;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public class ParameterSynchronizer extends Synchronizer {

    private static final Logger logger = Logger.getLogger(ParameterSynchronizer.class);
    private static final String TABLE_NAME = "Parameter";

    public static void main(String[] args) throws Exception {
        new ParameterSynchronizer().synchronize();
    }

    @Override
    public void synchronize()
            throws DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage,
            DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage {

        Parameter mostRecent = ParameterController.mostRecent();
        Data data;

        if (mostRecent == null) {
            // Retrieve all records
            data = getTable(TABLE_NAME, new Date());
        } else {
            // Retrieve new records
            data = getTableChanges(TABLE_NAME, tomorrow(mostRecent.getBeginDate()));
        }

        List<Parameter> parameters = new ArrayList<Parameter>();

        for (DataRow dataRow : data.getDataRow()) {
            Parameter parameter = new Parameter();
            parameter.setBeginDate(dataRow.getBeginDate());
            parameter.setEndDate(dataRow.getEndDate());
            for (DataField dataField : dataRow.getFields().getDataField()) {
                if (dataField.getName().equals("Code")) {
                    StringField stringDataField = (StringField) dataField;
                    parameter.setCode(stringDataField.getData());
                } else if (dataField.getName().equals("Omschrijving")) {
                    StringField stringDataField = (StringField) dataField;
                    parameter.setDescription(stringDataField.getData());
                } else if (dataField.getName().equals("CASnummer")) {
                    StringField stringDataField = (StringField) dataField;
                    parameter.setCasNumber(stringDataField.getData());
                } else if (dataField.getName().equals("Groep")) {
                    StringField stringDataField = (StringField) dataField;
                    parameter.setGroup(stringDataField.getData());
                } else if (dataField.getName().equals("SIKBid")) {
                    if (!dataField.isIsNull()) {
                        IntegerField integerDataField = (IntegerField) dataField;
                        parameter.setSikbId(integerDataField.getData());
                    }
                } else {
                    logger.warn("Unknown field: " + dataField.getName());
                }
            }
            parameters.add(parameter);
        }

        ParameterController.synchronize(parameters);

    }
}
