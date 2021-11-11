package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "homework")
public class Homework {

	@Id
	@Column(name = "homeworkid")
	private int homeworkId;

	@Column(name = "pid")
	private int pupilId;

	@Column(name = "subname")
	private String subName;

	@Column(name = "pname")
	private String pName;

	@Column(name = "date_create")
	private String dateCreate;

	@Column(name = "other_homeworkdetail")
	private String homeworkDetail;

	@Column(name = "teacher_id")
	private int teacherId;

	@Column(name = "subid")
	private int subId;

	public Homework() {
		super();
	}

	public Homework(int homeworkId, int pupilId, String subName, String pName, String dateCreate, String homeworkDetail,
			int teacherId, int subId) {
		super();
		this.homeworkId = homeworkId;
		this.pupilId = pupilId;
		this.subName = subName;
		this.pName = pName;
		this.dateCreate = dateCreate;
		this.homeworkDetail = homeworkDetail;
		this.teacherId = teacherId;
		this.subId = subId;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}

	public int getPupilId() {
		return pupilId;
	}

	public void setPupilId(int pupilId) {
		this.pupilId = pupilId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getHomeworkDetail() {
		return homeworkDetail;
	}

	public void setHomeworkDetail(String homeworkDetail) {
		this.homeworkDetail = homeworkDetail;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

}
