package com.ebay.raptor.promotion.pojo.business;

public enum PromotionType {
    Default(""), HotSell("HotSell"), DealsPreset("DealsPreset"), DealsUpload("DealsUpload"), Other("Other");

    PromotionType (String shortName){
        this.setShortName(shortName);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public static PromotionType getPromotionType (String typeStr) {
        if (typeStr == null) {
            return Default;
        }

        for (PromotionType type : PromotionType.values()) {
            if (typeStr.equalsIgnoreCase(type.getShortName())) {
                return  type;
            }
        }

        return Default;
    }

    private String shortName;
}
