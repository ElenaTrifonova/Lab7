package app;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Enumeration that contains type of organization
 */
public enum OrganizationType implements Serializable {
    PUBLIC("Public"),
    GOVERNMENT("Government"),
    TRUST("Trust"),
    PRIVATE_LIMITED_COMPANY("Private limited company"),
    OPEN_JOINT_STOCK_COMPANY("Open joint stock company");

    private final String name;

    OrganizationType(String name){
        this.name = name;
    }


    public static OrganizationType getValue(String value, String messageIfNull) {
        try {
            return Arrays.stream(OrganizationType.values()).filter(f -> f.ordinal()+1 == Integer.parseInt(value))
                    .findAny().orElse(null);
        } catch (NumberFormatException ignored){}
        System.out.println(messageIfNull);
        return null;
    }

}
