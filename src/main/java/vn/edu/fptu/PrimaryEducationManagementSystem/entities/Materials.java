package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "materials")
public class Materials {

	@Id
	@Column(name = "mater_id")
	private int masterId;

	@Column(name = "teacher_id")
	private int teacherId;

	@Column(name = "class_id")
	private int classId;

	@Column(name = "class_name")
	private String className;

	@Column(name = "mater_name")
	private String masterName;

	public Materials() {
		super();
	}

	public Materials(int masterId, int teacherId, int classId, String className, String masterName) {
		super();
		this.masterId = masterId;
		this.teacherId = teacherId;
		this.classId = classId;
		this.className = className;
		this.masterName = masterName;
	}

	public int getMasterId() {
		return masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

}
