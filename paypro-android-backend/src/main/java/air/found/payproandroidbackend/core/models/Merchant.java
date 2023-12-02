package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "merchants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String merchantName;

    @Embedded
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "merchant_card_brands",
            joinColumns = @JoinColumn(name = "merchant_id"),
            inverseJoinColumns = @JoinColumn(name = "card_brand_id")
    )
    private Set<CardBrand> acceptedCardsEnum;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status statusEnum;

    @OneToMany(mappedBy = "merchant")
    private List<Terminal> terminals;

    @ManyToMany
    @JoinTable(
            name = "user_merchants",
            joinColumns = @JoinColumn(name = "merchant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserAccount> userAccounts;

    @Transient
    private List<Integer> acceptedCards;

    @Transient
    private Integer status;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class Address {
        @Column(name = "street_name", nullable = false)
        private String streetName;

        @Column(name = "city_name", nullable = false, length = 100)
        private String city;

        @Column(name = "street_number", nullable = false, length = 15)
        private String streetNumber;

        @Column(name = "postal_code", nullable = false)
        private Integer postalCode;
    }
}
