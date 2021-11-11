package com.primary_education_system.enum_type;

public enum Gender {

    MALE("1"), FEMALE("2"), UNDEFINE("9");

    private String value;

    public String getValue() {
        return value;
    }

    public void setName(String value) {
        this.value = value;
    }

    Gender(String value) {
        this.value = value;
    }

}
