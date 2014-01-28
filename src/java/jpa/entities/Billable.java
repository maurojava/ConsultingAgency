package jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nbuser
 */
@Entity
@Table(name = "billable")
@NamedQueries({
    @NamedQuery(name = "Billable.findAll", query = "SELECT b FROM Billable b"),
    @NamedQuery(name = "Billable.findByBillableId", query = "SELECT b FROM Billable b WHERE b.billableId = :billableId"),
    @NamedQuery(name = "Billable.findByStartDate", query = "SELECT b FROM Billable b WHERE b.startDate = :startDate"),
    @NamedQuery(name = "Billable.findByEndDate", query = "SELECT b FROM Billable b WHERE b.endDate = :endDate"),
    @NamedQuery(name = "Billable.findByHours", query = "SELECT b FROM Billable b WHERE b.hours = :hours"),
    @NamedQuery(name = "Billable.findByHourlyRate", query = "SELECT b FROM Billable b WHERE b.hourlyRate = :hourlyRate"),
    @NamedQuery(name = "Billable.findByBillableHourlyRate", query = "SELECT b FROM Billable b WHERE b.billableHourlyRate = :billableHourlyRate"),
    @NamedQuery(name = "Billable.findByDescription", query = "SELECT b FROM Billable b WHERE b.description = :description")})
public class Billable implements Serializable {
    private static final long serialVersionUID = 1L;
        private Long billableId;
        private Date startDate;
        private Date endDate;
        private short hours;
        private BigDecimal hourlyRate;
        private BigDecimal billableHourlyRate;
   // @Column(name = "description")
    private String description;
        private String artifacts;
        private Project project;
        private Consultant consultantId;

    public Billable() {
    }

    public Billable(Long billableId) {
        this.billableId = billableId;
    }

    public Billable(Long billableId, short hours, BigDecimal hourlyRate, BigDecimal billableHourlyRate) {
        this.billableId = billableId;
        this.hours = hours;
        this.hourlyRate = hourlyRate;
        this.billableHourlyRate = billableHourlyRate;
    }

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "billable_id")
    public Long getBillableId() {
        return billableId;
    }

    public void setBillableId(Long billableId) {
        this.billableId = billableId;
    }

@Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

@Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

@Basic(optional = false)
    @Column(name = "hours")
    public short getHours() {
        return hours;
    }

    public void setHours(short hours) {
        this.hours = hours;
    }

@Basic(optional = false)
    @Column(name = "hourly_rate")
    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

@Basic(optional = false)
    @Column(name = "billable_hourly_rate")
    public BigDecimal getBillableHourlyRate() {
        return billableHourlyRate;
    }

    public void setBillableHourlyRate(BigDecimal billableHourlyRate) {
        this.billableHourlyRate = billableHourlyRate;
    }
   @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

@Lob
    @Column(name = "artifacts")
    public String getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(String artifacts) {
        this.artifacts = artifacts;
    }

@JoinColumns({
        @JoinColumn(name = "client_name", referencedColumnName = "client_name"),
        @JoinColumn(name = "client_department_number", referencedColumnName = "client_department_number"),
        @JoinColumn(name = "project_name", referencedColumnName = "project_name")})
    @ManyToOne(optional = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

@JoinColumn(name = "consultant_id", referencedColumnName = "consultant_id")
    @ManyToOne(optional = false)
    public Consultant getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Consultant consultantId) {
        this.consultantId = consultantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billableId != null ? billableId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Billable)) {
            return false;
        }
        Billable other = (Billable) object;
        if ((this.billableId == null && other.billableId != null) || (this.billableId != null && !this.billableId.equals(other.billableId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Billable[billableId=" + billableId + "]";
    }

}
