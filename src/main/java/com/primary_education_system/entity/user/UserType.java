package com.primary_education_system.entity.user;

public enum UserType {

    WEB_USER(1), APP_USER(2), KUSURI_USER(3);

    int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
