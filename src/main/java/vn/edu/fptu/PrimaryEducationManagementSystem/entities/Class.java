package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class Class {

	@Id
	@Column(name = "classid")
	private int classId;

	@Column(name = "pname")
	private String pName;

	@Column(name = "classname")
	private String className;

	@Column(name = "date_from")
	private String dateFrom;

	@Column(name = "date_to")
	private String dateTo;

	@Column(name = "teacher_id")
	private int teacherId;

	@Column(name = "adid")
	private int adId;

	@Column(name = "mater_id")
	private int mId;

	@Column(name = "tuid")
	private int tuId;

	@Column(name = "time_id")
	private int timeId;

	public Class() {
		super();
	}

	public Class(int classId, String pName, String className, String dateFrom, String dateTo, int teacherId,
			int adId, int mId, int tuId, int timeId) {
		super();
		this.classId = classId;
		this.pName = pName;
		this.className = className;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.teacherId = teacherId;
		this.adId = adId;
		this.mId = mId;
		this.tuId = tuId;
		this.timeId = timeId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getTuId() {
		return tuId;
	}

	public void setTuId(int tuId) {
		this.tuId = tuId;
	}

	public int getTimeId() {
		return timeId;
	}

	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}

}
