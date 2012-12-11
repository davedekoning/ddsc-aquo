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
@Table(name = "ddsc_core_parameter")
@NamedQueries({
    @NamedQuery(name = "Parameter.findByCode", query = "SELECT p FROM Parameter p WHERE p.code = :code"),
    @NamedQuery(name = "Parameter.mostRecent", query = "SELECT p FROM Parameter p ORDER BY p.beginDate DESC")
})
public class Parameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(columnDefinition = "CHAR(12)", nullable = false, unique = true)
    private String code;
    @Column(columnDefinition = "CHAR(60)", nullable = false, unique = true)
    private String description;
    @Column(name = "cas_number", columnDefinition = "CHAR(12)", nullable = false)
    private String casNumber;
    @Column(name = "\"group\"", columnDefinition = "CHAR(60)")
    private String group;
    @Column(name = "sikb_id", unique = true)
    private Integer sikbId;
    @Column(name = "begin_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date beginDate;
    @Column(name = "end_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;

    public Parameter() {
        super();
    }

    @Override
    public String toString() {
        return "Parameter{"
                + "code=" + code
                + ", description=" + description
                + ", casNumber=" + casNumber
                + ", group=" + group
                + ", sikbId=" + sikbId
                + ", beginDate=" + beginDate
                + ", endDate=" + endDate
                + '}';
    }

    public void update(Parameter parameter) {
        this.code = parameter.code;
        this.description = parameter.description;
        this.casNumber = parameter.casNumber;
        this.group = parameter.group;
        this.sikbId = parameter.sikbId;
        this.beginDate = parameter.beginDate;
        this.endDate = parameter.endDate;
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

    public String getCasNumber() {
        return casNumber;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getSikbId() {
        return sikbId;
    }

    public void setSikbId(Integer sikbId) {
        this.sikbId = sikbId;
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
