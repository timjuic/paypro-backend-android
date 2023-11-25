package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Statuses {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "status_id", nullable = false)
    private int statusId;
    @Basic
    @Column(name = "status_name", nullable = true, length = 20)
    private String statusName;
    @OneToMany(mappedBy = "statusEntity")
    private Collection<Merchants> merchantsByStatusId;

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

    public void setMerchantsByStatusId(Collection<Merchants> merchantsByStatusId) {
        this.merchantsByStatusId = merchantsByStatusId;
    }
}
