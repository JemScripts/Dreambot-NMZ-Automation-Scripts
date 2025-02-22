package Enum;

public enum GEAR {
    DHAROk_HELM("Dharok's helm"),
    DHAROK_BODY("Dharok's platebody"),
    DHAROK_LEG("Dharok's platelegs"),
    DHAROK_AXE("Dharok's greataxe"),
    FIRE_CAPE("Fire cape"),
    BERSERKER_RING("Berserker ring"),
    BARROWS("Barrows gloves"),
    FURY("Amulet of fury"),
    BOOT("Dragon boots");

    private String item;

    GEAR(String item) {
        this.item = item;
    }

    public String getItem(){
        return this.item = item;
    }
}