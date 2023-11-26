package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "terminals")
@Data
@NoArgsConstructor
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terminal_id")
    private Integer terminalId;

    @Column(name = "terminal_key", nullable = false, length = 20)
    private String terminalKey;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "merchant_merchant_id", referencedColumnName = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "status_status_id", referencedColumnName = "status_id")
    private Status status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
