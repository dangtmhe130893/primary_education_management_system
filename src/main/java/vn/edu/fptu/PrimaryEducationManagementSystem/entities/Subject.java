package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subject")
public class Subject {

	@Id
	@Column(name = "subid")
	private int subId;

	@Column(name = "teacher_id")
	private String teacherId;

	@Column(name = "subname")
	private String subName;

	@Column(name = "classid")
	private int classId;

	public Subject() {
		super();
	}

	public Subject(int subId, String teacherId, String subName, int classId) {
		super();
		this.subId = subId;
		this.teacherId = teacherId;
		this.subName = subName;
		this.classId = classId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

}
