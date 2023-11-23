package air.found.payproandroidbackend.core;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class UserMerchantsPK implements Serializable {
    @Column(name = "user_user_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userUserId;
    @Column(name = "merchant_merchant_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int merchantMerchantId;

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
        UserMerchantsPK that = (UserMerchantsPK) o;
        return userUserId == that.userUserId && merchantMerchantId == that.merchantMerchantId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userUserId, merchantMerchantId);
    }
}
