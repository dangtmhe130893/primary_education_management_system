package com.primary_education_system.entity;

public enum Roles {

	COMPANY_ADMIN("COMPANY_ADMIN"), SYSTEM_ADMIN("SYSTEM_ADMIN"), MOBILE_USER("MOBILE_USER");
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Roles(String name) {
		this.name = name;
	}
	
	
}
