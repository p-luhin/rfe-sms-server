package by.bsu.rfe.smsservice.common.enums;

/**
 * Created by pluhin on 1/4/17.
 */
public enum SmsServerProperty {

    //Mute Mode properties
    MUTE_ENABLED(SmsServerPropertyGroup.MUTE_MODE, "Mute Enabled"),
    MUTE_START_TIME(SmsServerPropertyGroup.MUTE_MODE, "Mute Start Time"),
    MUTE_END_TIME(SmsServerPropertyGroup.MUTE_MODE, "Mute End Time"),

    //Email address properties
    EMAIL_ENABLED(SmsServerPropertyGroup.EMAIL, "Email Enabled"),
    USERNAME(SmsServerPropertyGroup.EMAIL, "Username"),
    PASSWORD(SmsServerPropertyGroup.EMAIL, "Password"),
    SMTP_HOST(SmsServerPropertyGroup.EMAIL, "SMTP Host"),
    SMTP_PORT(SmsServerPropertyGroup.EMAIL, "SMTP Port"),
    AUTHENTICATION_ENABLED(SmsServerPropertyGroup.EMAIL, "Authentication Enabled"),
    TLS_ENABLED(SmsServerPropertyGroup.EMAIL, "TLS Enabled");

    private SmsServerPropertyGroup propertyGroup;
    private String displayValue;

    SmsServerProperty(SmsServerPropertyGroup propertyGroup, String displayValue) {
        this.propertyGroup = propertyGroup;
        this.displayValue = displayValue;
    }

    public SmsServerPropertyGroup getPropertyGroup() {
        return propertyGroup;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static SmsServerProperty getByDisplayName(String displayValue) {
        for (SmsServerProperty smsServerProperty : values()) {
            if (smsServerProperty.getDisplayValue().equals(displayValue)) {
                return smsServerProperty;
            }
        }
        return null;
    }

    public enum SmsServerPropertyGroup {
        MUTE_MODE("Mute Mode"),
        EMAIL("Email"),
        UNASSIGNED("Unassigned");

        private String displayValue;

        SmsServerPropertyGroup(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static SmsServerPropertyGroup getByDisplayName(String displayValue) {
            for (SmsServerPropertyGroup smsServerPropertyGroup : values()) {
                if (smsServerPropertyGroup.getDisplayValue().equals(displayValue)) {
                    return smsServerPropertyGroup;
                }
            }
            return null;
        }
    }
}
