package jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author nbuser
 */
@Entity
@Table(name = "consultant_status")
@NamedQueries({
    @NamedQuery(name = "ConsultantStatus.findAll", query = "SELECT c FROM ConsultantStatus c"),
    @NamedQuery(name = "ConsultantStatus.findByStatusId", query = "SELECT c FROM ConsultantStatus c WHERE c.statusId = :statusId"),
    @NamedQuery(name = "ConsultantStatus.findByDescription", query = "SELECT c FROM ConsultantStatus c WHERE c.description = :description")})
public class ConsultantStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    private Character statusId;
    // @Basic(optional = false)
    //  @Column(name = "description")
    private String description;
    private Collection<Consultant> consultantCollection;

    public ConsultantStatus() {
    }

    public ConsultantStatus(Character statusId) {
        this.statusId = statusId;
    }

    public ConsultantStatus(Character statusId, String description) {
        this.statusId = statusId;
        this.description = description;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "status_id")
    public Character getStatusId() {
        return statusId;
    }

    public void setStatusId(Character statusId) {
        this.statusId = statusId;
    }

    @Basic(optional = false)
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId")
    public Collection<Consultant> getConsultantCollection() {
        return consultantCollection;
    }

    public void setConsultantCollection(Collection<Consultant> consultantCollection) {
        this.consultantCollection = consultantCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsultantStatus)) {
            return false;
        }
        ConsultantStatus other = (ConsultantStatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.ConsultantStatus[statusId=" + statusId + "]";
    }

}
