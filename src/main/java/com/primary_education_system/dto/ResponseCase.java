package com.primary_education_system.dto;

public interface ResponseCase {

    ResponseStatus SUCCESS = new ResponseStatus(1000, "SUCCESS");
    ResponseStatus EMAIL_EXISTED = new ResponseStatus(1001, "Email existed!");

    ResponseStatus ERROR_PASSWORD = new ResponseStatus(1002, "Error password");

    ResponseStatus ERROR = new ResponseStatus(4, "ERROR");

}
