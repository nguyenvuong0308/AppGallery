package gallery.vnm.com.appgallery.model;

public enum EnumFlag {
    ON,
    OFF,
    UPDATE_REQUIRED,
    DEFAULT;

    public static EnumFlag getValue(String value) {
        switch (value) {
            case "on":
                return ON;
            case "off":
                return OFF;
            case "update_required":
                return UPDATE_REQUIRED;
            default:
                return DEFAULT;
        }
    }
}
