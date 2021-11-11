package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class Teacher {
	
	@Id
	@Column(name = "teacher_id")
	private int teacherId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "date_of_birth")
	private String dateOfBirith;

	@Column(name = "gender")
	private int gender;

	@Column(name = "teacher_address")
	private String teacherAddress;

	@Column(name = "teacher_contact")
	private String teacherContact;

	@Column(name = "accid")
	private int accId;

	public Teacher() {
		super();
	}

	public Teacher(int teacherId, String firstName, String lastName, String dateOfBirith, int gender,
			String teacherAddress, String teacherContact, int accId) {
		super();
		this.teacherId = teacherId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirith = dateOfBirith;
		this.gender = gender;
		this.teacherAddress = teacherAddress;
		this.teacherContact = teacherContact;
		this.accId = accId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirith() {
		return dateOfBirith;
	}

	public void setDateOfBirith(String dateOfBirith) {
		this.dateOfBirith = dateOfBirith;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getTeacherAddress() {
		return teacherAddress;
	}

	public void setTeacherAddress(String teacherAddress) {
		this.teacherAddress = teacherAddress;
	}

	public String getTeacherContact() {
		return teacherContact;
	}

	public void setTeacherContact(String teacherContact) {
		this.teacherContact = teacherContact;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

}
