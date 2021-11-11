package com.primary_education_system.entity.token;

public enum TypeUserToken {

    USER(1), MEMBER(2);

    int value;

    TypeUserToken(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
