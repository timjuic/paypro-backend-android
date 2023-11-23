package air.found.payproandroidbackend.core;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

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
    @Basic
    @Column(name = "merchant_merchant_id", nullable = true)
    private Integer merchantMerchantId;
    @Basic
    @Column(name = "status_status_id", nullable = true)
    private Integer statusStatusId;
    @ManyToOne
    @JoinColumn(name = "merchant_merchant_id", referencedColumnName = "merchant_id")
    private Merchants merchantsByMerchantMerchantId;
    @ManyToOne
    @JoinColumn(name = "status_status_id", referencedColumnName = "status_id")
    private Statuses statusesByStatusStatusId;

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalKey() {
        return terminalKey;
    }

    public void setTerminalKey(String terminalKey) {
        this.terminalKey = terminalKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getMerchantMerchantId() {
        return merchantMerchantId;
    }

    public void setMerchantMerchantId(Integer merchantMerchantId) {
        this.merchantMerchantId = merchantMerchantId;
    }

    public Integer getStatusStatusId() {
        return statusStatusId;
    }

    public void setStatusStatusId(Integer statusStatusId) {
        this.statusStatusId = statusStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminals terminals = (Terminals) o;
        return terminalId == terminals.terminalId && type == terminals.type && Objects.equals(terminalKey, terminals.terminalKey) && Objects.equals(createdAt, terminals.createdAt) && Objects.equals(merchantMerchantId, terminals.merchantMerchantId) && Objects.equals(statusStatusId, terminals.statusStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminalId, terminalKey, type, createdAt, merchantMerchantId, statusStatusId);
    }

    public Merchants getMerchantsByMerchantMerchantId() {
        return merchantsByMerchantMerchantId;
    }

    public void setMerchantsByMerchantMerchantId(Merchants merchantsByMerchantMerchantId) {
        this.merchantsByMerchantMerchantId = merchantsByMerchantMerchantId;
    }

    public Statuses getStatusesByStatusStatusId() {
        return statusesByStatusStatusId;
    }

    public void setStatusesByStatusStatusId(Statuses statusesByStatusStatusId) {
        this.statusesByStatusStatusId = statusesByStatusStatusId;
    }
}
