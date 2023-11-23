package air.found.payproandroidbackend.core;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_merchants", schema = "public", catalog = "air_db")
@IdClass(UserMerchantsPK.class)
public class UserMerchants {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_user_id", nullable = false)
    private int userUserId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "merchant_merchant_id", nullable = false)
    private int merchantMerchantId;
    @ManyToOne
    @JoinColumn(name = "merchant_merchant_id", referencedColumnName = "merchant_id", nullable = false)
    private Merchants merchantsByMerchantMerchantId;

    public int getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(int userUserId) {
        this.userUserId = userUserId;
    }

    public int getMerchantMerchantId() {
        return merchantMerchantId;
    }

    public void setMerchantMerchantId(int merchantMerchantId) {
        this.merchantMerchantId = merchantMerchantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMerchants that = (UserMerchants) o;
        return userUserId == that.userUserId && merchantMerchantId == that.merchantMerchantId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userUserId, merchantMerchantId);
    }

    public Merchants getMerchantsByMerchantMerchantId() {
        return merchantsByMerchantMerchantId;
    }

    public void setMerchantsByMerchantMerchantId(Merchants merchantsByMerchantMerchantId) {
        this.merchantsByMerchantMerchantId = merchantsByMerchantMerchantId;
    }
}
