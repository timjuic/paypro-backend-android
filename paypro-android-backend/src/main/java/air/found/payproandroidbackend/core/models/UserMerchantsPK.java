package air.found.payproandroidbackend.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class UserMerchantsPK implements Serializable {
    @Column(name = "user_user_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userUserId;
    @Column(name = "merchant_merchant_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int merchantMerchantId;

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
