package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff {

	@Id
	@Column(name = "staffid")
	private int staffId;

	@Column(name = "staffname")
	private String staffName;

	@Column(name = "staffadress")
	private String staffAddress;

	@Column(name = "staffcontract")
	private String staffContact;

	@Column(name = "gender")
	private int gender;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "accid")
	private int accId;
	
	public Staff() {
		super();
	}

	public Staff(int staffId, String staffName, String staffAddress, String staffContact, int gender,
			String dateOfBirth, int accId) {
		super();
		this.staffId = staffId;
		this.staffName = staffName;
		this.staffAddress = staffAddress;
		this.staffContact = staffContact;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.accId = accId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}

	public String getStaffContact() {
		return staffContact;
	}

	public void setStaffContact(String staffContact) {
		this.staffContact = staffContact;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

}
