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
@Table(name = "ddsc_core_processingmethod")
@NamedQueries({
    @NamedQuery(name = "ProcessingMethod.findByCode", query = "SELECT pm FROM ProcessingMethod pm WHERE pm.code = :code"),
    @NamedQuery(name = "ProcessingMethod.mostRecent", query = "SELECT pm FROM ProcessingMethod pm ORDER BY pm.beginDate DESC")
})
public class ProcessingMethod implements Serializable {

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
    @Column(name = "begin_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date beginDate;
    @Column(name = "end_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;

    public ProcessingMethod() {
        super();
    }

    @Override
    public String toString() {
        return "ProcessingMethod{"
                + "code=" + code
                + ", description=" + description
                + ", group=" + group
                + ", beginDate=" + beginDate
                + ", endDate=" + endDate
                + '}';
    }

    public void update(ProcessingMethod method) {
        this.code = method.code;
        this.description = method.description;
        this.group = method.group;
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
