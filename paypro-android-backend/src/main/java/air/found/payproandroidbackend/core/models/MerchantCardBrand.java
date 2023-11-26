package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "merchant_card_brands")
@Data
@NoArgsConstructor
@IdClass(MerchantCardBrand.MerchantCardBrandId.class)
public class MerchantCardBrand {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "merchant_merchant_id", referencedColumnName = "merchant_id")
    private Merchant merchant;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_brand_card_brand_id", referencedColumnName = "card_brand_id")
    private CardBrand cardBrand;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MerchantCardBrandId implements Serializable {
        private Integer merchant;
        private Integer cardBrand;
    }
}
