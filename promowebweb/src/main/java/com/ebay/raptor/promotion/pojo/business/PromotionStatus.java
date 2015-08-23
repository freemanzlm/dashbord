package com.ebay.raptor.promotion.pojo.business;

public enum PromotionStatus {
	Default(""), Created("Created"), OwnerConfirmed ("OwnerConfirmed"),
	Enrolled("Enrolled"), EnrollFirstAudited("EnrollFirstAudited"), EnrollSubmitted("EnrollSubmited"), EnrollAudited("EnrollAudited"),
	Start("Start"), ShutDown("ShutDown"),
	SubsidyAudited("SubsidyAudited"), SubsidyApplied("SubsidyApplied"),
	End("End");
	
	PromotionStatus (String shortName){
        this.setShortName(shortName);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public static PromotionStatus getConversionType (String typeStr) {
        if (typeStr == null) {
            return Default;
        }

        for (PromotionStatus status : PromotionStatus.values()) {
            if (typeStr.equalsIgnoreCase(status.getShortName())) {
                return  status;
            }
        }

        return Default;
    }

    private String shortName;
}
