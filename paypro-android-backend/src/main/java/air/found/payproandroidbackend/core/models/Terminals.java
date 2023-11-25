package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Terminals {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "terminal_id", nullable = false)
    private int terminalId;
    @Basic
    @Column(name = "terminal_key", nullable = false, length = 20)
    private String terminalKey;
    @Basic
    @Column(name = "type", nullable = false)
    private int type;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "merchant_merchant_id", referencedColumnName = "merchant_id")
    private Merchants merchantsByMerchantMerchantId;
    @ManyToOne
    @JoinColumn(name = "status_status_id", referencedColumnName = "status_id")
    private Statuses statusesByStatusStatusId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminals terminals = (Terminals) o;
        return terminalId == terminals.terminalId && type == terminals.type && Objects.equals(terminalKey, terminals.terminalKey) && Objects.equals(createdAt, terminals.createdAt) && Objects.equals(merchantsByMerchantMerchantId, terminals.merchantsByMerchantMerchantId) && Objects.equals(statusesByStatusStatusId, terminals.statusesByStatusStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminalId, terminalKey, type, createdAt, merchantsByMerchantMerchantId, statusesByStatusStatusId);
    }

    public void setMerchantsByMerchantMerchantId(Merchants merchantsByMerchantMerchantId) {
        this.merchantsByMerchantMerchantId = merchantsByMerchantMerchantId;
    }

    public void setStatusesByStatusStatusId(Statuses statusesByStatusStatusId) {
        this.statusesByStatusStatusId = statusesByStatusStatusId;
    }
}
