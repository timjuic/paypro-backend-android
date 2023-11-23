package air.found.payproandroidbackend.core;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

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

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
        Merchants merchants = (Merchants) o;
        return merchantId == merchants.merchantId && postalCode == merchants.postalCode && Objects.equals(fullName, merchants.fullName) && Objects.equals(status, merchants.status) && Objects.equals(streetName, merchants.streetName) && Objects.equals(cityName, merchants.cityName) && Objects.equals(streetNumber, merchants.streetNumber) && Objects.equals(createdAt, merchants.createdAt) && Objects.equals(statusStatusId, merchants.statusStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, fullName, status, streetName, cityName, postalCode, streetNumber, createdAt, statusStatusId);
    }

    public Collection<MerchantCardBrands> getMerchantCardBrandsByMerchantId() {
        return merchantCardBrandsByMerchantId;
    }

    public void setMerchantCardBrandsByMerchantId(Collection<MerchantCardBrands> merchantCardBrandsByMerchantId) {
        this.merchantCardBrandsByMerchantId = merchantCardBrandsByMerchantId;
    }
}
