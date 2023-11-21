package air.found.payproandroidbackend.core;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Merchant {
    @Id
    private Integer merchantID;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;
    private String streetName;
    private String cityName;
    private Integer postalCode;
    private String streetNumber;
    private java.sql.Date dateCreated;
}