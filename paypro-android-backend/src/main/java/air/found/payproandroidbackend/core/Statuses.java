package air.found.payproandroidbackend.core;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Statuses {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "status_id", nullable = false)
    private int statusId;
    @Basic
    @Column(name = "status_name", nullable = true, length = 20)
    private String statusName;
    @OneToMany(mappedBy = "statusesByStatusStatusId")
    private Collection<Merchants> merchantsByStatusId;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statuses statuses = (Statuses) o;
        return statusId == statuses.statusId && Objects.equals(statusName, statuses.statusName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, statusName);
    }

    public Collection<Merchants> getMerchantsByStatusId() {
        return merchantsByStatusId;
    }

    public void setMerchantsByStatusId(Collection<Merchants> merchantsByStatusId) {
        this.merchantsByStatusId = merchantsByStatusId;
    }
}
