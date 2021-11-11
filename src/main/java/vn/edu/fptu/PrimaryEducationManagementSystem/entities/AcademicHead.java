package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACADEMIC_HEAD")
public class AcademicHead {

	@Id
	@Column(name = "adid")
	private int adId;

	@Column(name = "adname")
	private String adName;

	@Column(name = "mcontact")
	private String mContact;

	@Column(name = "maddress")
	private String mAddress;

	@Column(name = "gender")
	private int gender;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "accid")
	private int accId;

	public AcademicHead() {
		super();
	}

	public AcademicHead(int adId, String adName, String mContact, String mAddress, int gender, String dateOfBirth,
			int accId) {
		super();
		this.adId = adId;
		this.adName = adName;
		this.mContact = mContact;
		this.mAddress = mAddress;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.accId = accId;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getmContact() {
		return mContact;
	}

	public void setmContact(String mContact) {
		this.mContact = mContact;
	}

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
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
