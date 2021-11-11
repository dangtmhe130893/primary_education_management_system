package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(name = "headmaster")
public class HeadMaster {

	@Id
	@Column(name = "mid")
	private int mId;

	@Column(name = "mname")
	private String mName;

	@Column(name = "maddress")
	private String mAddress;

	@Column(name = "mcontact")
	private String mContact;

	@Column(name = "accid")
	private String accId;

	public HeadMaster() {
		super();
	}

	public HeadMaster(int mId, String mName, String mAddress, String mContact, String accId) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mAddress = mAddress;
		this.mContact = mContact;
		this.accId = accId;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public String getmContact() {
		return mContact;
	}

	public void setmContact(String mContact) {
		this.mContact = mContact;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

}
