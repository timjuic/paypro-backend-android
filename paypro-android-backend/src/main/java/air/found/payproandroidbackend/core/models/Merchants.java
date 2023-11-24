package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Merchants {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "merchant_id", nullable = false)
    private int merchantId;
    @Basic
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;
    @Basic
    @Column(name = "street_name", nullable = false, length = 255)
    private String streetName;
    @Basic
    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;
    @Basic
    @Column(name = "postal_code", nullable = false)
    private int postalCode;
    @Basic
    @Column(name = "street_number", nullable = false, length = 15)
    private String streetNumber;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "status_status_id", nullable = true)
    private Integer statusStatusId;
    @OneToMany(mappedBy = "merchantsByMerchantMerchantId")
    private Collection<MerchantCardBrands> merchantCardBrandsByMerchantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchants merchants = (Merchants) o;
        return merchantId == merchants.merchantId && postalCode == merchants.postalCode && Objects.equals(fullName, merchants.fullName) && Objects.equals(status, merchants.status) && Objects.equals(streetName, merchants.streetName) && Objects.equals(cityName, merchants.cityName) && Objects.equals(streetNumber, merchants.streetNumber) && Objects.equals(createdAt, merchants.createdAt) && Objects.equals(statusStatusId, merchants.statusStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, fullName, status, streetName, cityName, postalCode, streetNumber, createdAt, statusStatusId);
    }

    public void setMerchantCardBrandsByMerchantId(Collection<MerchantCardBrands> merchantCardBrandsByMerchantId) {
        this.merchantCardBrandsByMerchantId = merchantCardBrandsByMerchantId;
    }
}
