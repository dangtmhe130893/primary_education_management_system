package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tuitionfee")
public class TuitionFee {

	@Id
	@Column(name = "tuid")
	private int tuId;

	@Column(name = "subid")
	private int subId;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "date")
	private String date;

	@Column(name = "mid")
	private int mId;

	@Column(name = "pid")
	private int pId;

	@Column(name = "staffid")
	private int staffId;

	@Column(name = "classid")
	private int classId;

	public TuitionFee() {
		super();
	}

	public TuitionFee(int tuId, int subId, BigDecimal price, String date, int mId, int pId, int staffId, int classId) {
		super();
		this.tuId = tuId;
		this.subId = subId;
		this.price = price;
		this.date = date;
		this.mId = mId;
		this.pId = pId;
		this.staffId = staffId;
		this.classId = classId;
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

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

}
