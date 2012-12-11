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
@Table(name = "ddsc_core_measuringmethod")
@NamedQueries({
    @NamedQuery(name = "MeasuringMethod.findByCode", query = "SELECT mm FROM MeasuringMethod mm WHERE mm.code = :code"),
    @NamedQuery(name = "MeasuringMethod.mostRecent", query = "SELECT mm FROM MeasuringMethod mm ORDER BY mm.beginDate DESC")
})
public class MeasuringMethod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(columnDefinition = "CHAR(12)", nullable = false, unique = true)
    private String code;
    @Column(columnDefinition = "CHAR(60)", nullable = false, unique = true)
    private String description;
    @Column(name = "\"group\"", columnDefinition = "CHAR(60)")
    private String group;
    @Column(columnDefinition = "CHAR(600)")
    private String titel;
    @Column(name = "begin_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date beginDate;
    @Column(name = "end_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;

    public MeasuringMethod() {
        super();
    }

    @Override
    public String toString() {
        return "MeasuringMethod{"
                + "code=" + code
                + ", description=" + description
                + ", group=" + group
                + ", titel=" + titel
                + ", beginDate=" + beginDate
                + ", endDate=" + endDate
                + '}';
    }

    public void update(MeasuringMethod method) {
        this.code = method.code;
        this.description = method.description;
        this.group = method.group;
        this.titel = method.titel;
        this.beginDate = method.beginDate;
        this.endDate = method.endDate;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
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
