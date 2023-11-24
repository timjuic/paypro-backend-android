package air.found.payproandroidbackend.core.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "merchant_card_brands", schema = "public", catalog = "air_db")
@IdClass(MerchantCardBrandsPK.class)
public class MerchantCardBrands {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "merchant_merchant_id", nullable = false)
    private int merchantMerchantId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "card_brand_card_brand_id", nullable = false)
    private int cardBrandCardBrandId;
    @ManyToOne
    @JoinColumn(name = "card_brand_card_brand_id", referencedColumnName = "card_brand_id", nullable = false)
    private CardBrands cardBrandsByCardBrandCardBrandId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantCardBrands that = (MerchantCardBrands) o;
        return merchantMerchantId == that.merchantMerchantId && cardBrandCardBrandId == that.cardBrandCardBrandId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantMerchantId, cardBrandCardBrandId);
    }

    public void setCardBrandsByCardBrandCardBrandId(CardBrands cardBrandsByCardBrandCardBrandId) {
        this.cardBrandsByCardBrandCardBrandId = cardBrandsByCardBrandCardBrandId;
    }
}
