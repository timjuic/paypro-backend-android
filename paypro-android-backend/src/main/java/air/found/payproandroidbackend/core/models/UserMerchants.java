package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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

    public void setMerchantsByMerchantMerchantId(Merchants merchantsByMerchantMerchantId) {
        this.merchantsByMerchantMerchantId = merchantsByMerchantMerchantId;
    }
}
