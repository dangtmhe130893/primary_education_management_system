package com.primary_education_system.dto;

public interface ResponseCase {

    ResponseStatus SUCCESS = new ResponseStatus(1000, "SUCCESS");
    ResponseStatus EMAIL_EXISTED = new ResponseStatus(1001, "Email existed!");

    ResponseStatus ERROR_PASSWORD = new ResponseStatus(1002, "Error password");

    ResponseStatus SAME_TIME_SCHEDULE = new ResponseStatus(1003, "Same time schedule");

    ResponseStatus SAME_NAME_CLASS = new ResponseStatus(1004, "Same name class");

    ResponseStatus ERROR = new ResponseStatus(4, "ERROR");

}
