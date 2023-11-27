package air.found.payproandroidbackend.core.enums;

import lombok.Getter;

@Getter
public enum CardBrandType {
    DINERS(1, "Diners"),
    MASTERCARD(2, "MasterCard"),
    VISA(3, "VISA"),
    Discover(4, "Discover"),
    MAESTRO(5, "Maestro");

    private final int id;
    private final String name;

    CardBrandType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
