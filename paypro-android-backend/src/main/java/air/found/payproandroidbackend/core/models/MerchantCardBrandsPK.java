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
public class MerchantCardBrandsPK implements Serializable {
    @Column(name = "merchant_merchant_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int merchantMerchantId;
    @Column(name = "card_brand_card_brand_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardBrandCardBrandId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantCardBrandsPK that = (MerchantCardBrandsPK) o;
        return merchantMerchantId == that.merchantMerchantId && cardBrandCardBrandId == that.cardBrandCardBrandId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantMerchantId, cardBrandCardBrandId);
    }
}