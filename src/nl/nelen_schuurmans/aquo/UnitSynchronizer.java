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
public class UnitSynchronizer extends Synchronizer {

    private static final Logger logger = Logger.getLogger(UnitSynchronizer.class);
    private static final String TABLE_NAME = "Eenheid";

    public static void main(String[] args) throws Exception {
        new UnitSynchronizer().synchronize();
    }

    @Override
    public void synchronize()
            throws DomainTableServiceGetDomainTableDomainTableFaultFaultFaultMessage,
            DomainTableServiceGetDomainTableChangesDomainTableFaultFaultFaultMessage {

        Unit mostRecent = UnitController.mostRecent();
        Data data;

        if (mostRecent == null) {
            // Retrieve all records
            data = getTable(TABLE_NAME, new Date());
        } else {
            // Retrieve new records
            data = getTableChanges(TABLE_NAME, tomorrow(mostRecent.getBeginDate()));
        }

        List<Unit> units = new ArrayList<Unit>();

        for (DataRow dataRow : data.getDataRow()) {
            Unit unit = new Unit();
            unit.setBeginDate(dataRow.getBeginDate());
            unit.setEndDate(dataRow.getEndDate());
            for (DataField dataField : dataRow.getFields().getDataField()) {
                if (dataField.getName().equals("Code")) {
                    StringField stringDataField = (StringField) dataField;
                    unit.setCode(stringDataField.getData());
                } else if (dataField.getName().equals("Omschrijving")) {
                    StringField stringDataField = (StringField) dataField;
                    unit.setDescription(stringDataField.getData());
                } else if (dataField.getName().equals("Omrekenfactor")) {
                    StringField stringDataField = (StringField) dataField;
                    unit.setConversionFactor(stringDataField.getData());
                } else if (dataField.getName().equals("Dimensie")) {
                    StringField stringDataField = (StringField) dataField;
                    unit.setDimension(stringDataField.getData());
                } else if (dataField.getName().equals("Groep")) {
                    StringField stringDataField = (StringField) dataField;
                    unit.setGroup(stringDataField.getData());
                } else {
                    logger.warn("Unknown field: " + dataField.getName());
                }
            }
            units.add(unit);
        }

        UnitController.synchronize(units);

    }
}
