package air.found.payproandroidbackend.core.enums;

import lombok.Getter;

@Getter
public enum StatusType {
    ACTIVE(1, "Active"),
    DISABLED(2, "Disabled"),
    PENDING(3, "Pending");

    private final int id;
    private final String name;

    StatusType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String getNameById(int id) {
        for (StatusType status : StatusType.values()) {
            if (status.getId() == id) {
                return status.getName();
            }
        }
        return null;
    }
}
