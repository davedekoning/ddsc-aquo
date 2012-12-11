/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
@Entity
@Table(name = "ddsc_core_unit")
@NamedQueries({
    @NamedQuery(name = "Unit.findByCode", query = "SELECT u FROM Unit u WHERE u.code = :code"),
    @NamedQuery(name = "Unit.mostRecent", query = "SELECT u FROM Unit u ORDER BY u.beginDate DESC")
})
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(columnDefinition = "CHAR(12)", nullable = false, unique = true)
    private String code;
    @Column(columnDefinition = "CHAR(60)", nullable = false, unique = true)
    private String description;
    @Column(columnDefinition = "CHAR(12)")
    private String dimension;
    @Column(name = "conversion_factor", columnDefinition = "CHAR(12)")
    private String conversionFactor;
    @Column(name = "\"group\"", columnDefinition = "CHAR(60)")
    private String group;
    @Column(name = "begin_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date beginDate;
    @Column(name = "end_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;

    public Unit() {
        super();
    }

    @Override
    public String toString() {
        return "Unit{"
                + "code=" + code
                + ", description=" + description
                + ", dimension=" + dimension
                + ", conversionFactor=" + conversionFactor
                + ", group=" + group
                + ", beginDate=" + beginDate
                + ", endDate=" + endDate
                + '}';
    }

    public void update(Unit unit) {
        this.code = unit.code;
        this.description = unit.description;
        this.dimension = unit.dimension;
        this.conversionFactor = unit.conversionFactor;
        this.group = unit.group;
        this.beginDate = unit.beginDate;
        this.endDate = unit.endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(String conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
