package air.found.payproandroidbackend.core;

import jakarta.persistence.*;

import java.util.Objects;

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

    public int getMerchantMerchantId() {
        return merchantMerchantId;
    }

    public void setMerchantMerchantId(int merchantMerchantId) {
        this.merchantMerchantId = merchantMerchantId;
    }

    public int getCardBrandCardBrandId() {
        return cardBrandCardBrandId;
    }

    public void setCardBrandCardBrandId(int cardBrandCardBrandId) {
        this.cardBrandCardBrandId = cardBrandCardBrandId;
    }

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

    public CardBrands getCardBrandsByCardBrandCardBrandId() {
        return cardBrandsByCardBrandCardBrandId;
    }

    public void setCardBrandsByCardBrandCardBrandId(CardBrands cardBrandsByCardBrandCardBrandId) {
        this.cardBrandsByCardBrandCardBrandId = cardBrandsByCardBrandCardBrandId;
    }
}
