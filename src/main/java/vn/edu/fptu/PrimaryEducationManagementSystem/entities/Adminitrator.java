package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(name = "adminitrator")
public class Adminitrator {

	@Id
	@Column(name = "ad_id")
	private int adId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_Name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "admin_address")
	private String address;

	@Column(name = "mcontact")
	private String contact;

	@Column(name = "page")
	private int page;

	@Column(name = "gender")
	private int gender;

	@Column(name = "acc_Id")
	private int accId;

	public Adminitrator() {
		super();
	}

	public Adminitrator(int adId, String firstName, String lastName, String middleName, String dateOfBirth,
			String address, String contact, int page, int gender, int accId) {
		super();
		this.adId = adId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.contact = contact;
		this.page = page;
		this.gender = gender;
		this.accId = accId;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

}
