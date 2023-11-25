package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_merchants")
@Data
@NoArgsConstructor
@IdClass(UserMerchant.UserMerchantId.class)
public class UserMerchant {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_user_id", referencedColumnName = "user_id")
    private UserAccount userAccount;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "merchant_merchant_id", referencedColumnName = "merchant_id")
    private Merchant merchant;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserMerchantId implements java.io.Serializable {
        private Integer userAccount;
        private Integer merchant;
    }
}