package vn.edu.fptu.PrimaryEducationManagementSystem.dto;

import java.math.BigDecimal;

public class TuitionFeeResponse {

	private int tuId;

	private int subId;

	private String subName;

	private BigDecimal price;

	private String date;

	public TuitionFeeResponse() {
		super();
	}

	public TuitionFeeResponse(int tuId, int subId, String subName, BigDecimal price, String date) {
		super();
		this.tuId = tuId;
		this.subId = subId;
		this.subName = subName;
		this.price = price;
		this.date = date;
	}

	public int getTuId() {
		return tuId;
	}

	public void setTuId(int tuId) {
		this.tuId = tuId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
