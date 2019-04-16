package gallery.vnm.com.appgallery.model;

public enum EnumFlag {
    ON,
    OFF,
    UPDATE_REQUIRED,
    LINK,
    DEFAULT;

    public static EnumFlag getValue(String value) {
        switch (value) {
            case "on":
                return ON;
            case "off":
                return OFF;
            case "update_required":
                return UPDATE_REQUIRED;
            case "link":
                return LINK;
            default:
                return DEFAULT;
        }
    }
}
