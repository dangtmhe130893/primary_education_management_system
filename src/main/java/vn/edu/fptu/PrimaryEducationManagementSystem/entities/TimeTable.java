package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timetable")
public class TimeTable {

	@Id
	@Column(name = "time_id")
	private int timeId;

	@Column(name = "subid")
	private int subId;

	@Column(name = "teacher_id")
	private int teacherId;

	@Column(name = "time_start")
	private String timeStart;

	@Column(name = "time_end")
	private String timeEnd;

	@Column(name = "day")
	private Date day;

	@Column(name = "classid")
	private int classId;

	public TimeTable() {
		super();
	}

	public TimeTable(int timeId, int subId, int teacherId, String timeStart, String timeEnd, Date day, int classId) {
		super();
		this.timeId = timeId;
		this.subId = subId;
		this.teacherId = teacherId;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.day = day;
		this.classId = classId;
	}

	public int getTimeId() {
		return timeId;
	}

	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

}
