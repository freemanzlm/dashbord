package com.ebay.raptor.promotion.pojo.business;

/**
 * 
 * @author lyan2
 */
public class Subsidy {
	private String promoId;
	
	private long oracleId;
	
	private int type;
	
	private String currency;
	
	private double amount;
	
	private float PO;
	
	private double WLT;
	
	private String status;
	
	private String sellerRegion;

	public String getSellerRegion() {
		return sellerRegion;
	}

	public void setSellerRegion(String sellerRegion) {
		this.sellerRegion = sellerRegion;
	}

	public String getPromoId() {
		return promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public long getOracleId() {
		return oracleId;
	}

	public void setOracleId(long oracleId) {
		this.oracleId = oracleId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public float getPO() {
		return PO;
	}

	public void setPO(float pO) {
		PO = pO;
	}

	public double getWLT() {
		return WLT;
	}

	public void setWLT(double wLT) {
		WLT = wLT;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
