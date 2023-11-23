package air.found.payproandroidbackend.core;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "card_brands", schema = "public", catalog = "air_db")
public class CardBrands {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "card_brand_id", nullable = false)
    private int cardBrandId;
    @Basic
    @Column(name = "name", nullable = true, length = 100)
    private String name;

    public int getCardBrandId() {
        return cardBrandId;
    }

    public void setCardBrandId(int cardBrandId) {
        this.cardBrandId = cardBrandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardBrands that = (CardBrands) o;
        return cardBrandId == that.cardBrandId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardBrandId, name);
    }
}
