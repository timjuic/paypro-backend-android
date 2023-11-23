package air.found.payproandroidbackend.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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
