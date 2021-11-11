package com.primary_education_system.entity.user;

public enum StatusUser {

	UN_ACTIVE(1), ACTIVE(2);
	private int value;

	StatusUser(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
