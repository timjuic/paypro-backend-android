package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "merchants")
@Data
@NoArgsConstructor
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private Integer merchantId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "status_status_id", referencedColumnName = "status_id")
    private Status status;

    @Column(name = "street_name", nullable = false, length = 255)
    private String streetName;

    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @Column(name = "street_number", nullable = false, length = 15)
    private String streetNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "merchant")
    private Set<MerchantCardBrand> merchantCardBrands;

    @OneToMany(mappedBy = "merchant")
    private Set<Terminal> terminals;

    @OneToMany(mappedBy = "merchant")
    private Set<UserMerchant> userMerchants;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
