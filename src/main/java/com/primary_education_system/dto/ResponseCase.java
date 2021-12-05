package com.primary_education_system.dto;

public interface ResponseCase {

    ResponseStatus SUCCESS = new ResponseStatus(1000, "SUCCESS");
    ResponseStatus EMAIL_EXISTED = new ResponseStatus(1001, "Email existed!");

    ResponseStatus ERROR_PASSWORD = new ResponseStatus(1002, "Error password");

    ResponseStatus SAME_TIME_SCHEDULE = new ResponseStatus(1003, "Same time schedule");

    ResponseStatus SAME_NAME_CLASS = new ResponseStatus(1004, "Same name class");

    ResponseStatus NAME_ROOM_EXIST = new ResponseStatus(1005, "Name room exist");

    ResponseStatus SAME_NAME_ROOM = new ResponseStatus(1006, "Same name room");

    ResponseStatus EMAIL_EXIST = new ResponseStatus(1007, "Same email");

    ResponseStatus SAME_EMAIL = new ResponseStatus(1008, "Same email");

    ResponseStatus DATA_IMPORT_EMPTY = new ResponseStatus(1009, "Data import empty");

    ResponseStatus DATA_IMPORT_ERROR_FORMAT = new ResponseStatus(1010, "Data import error format");

    ResponseStatus NAME_CLASS_NOT_FOUND = new ResponseStatus(1011, "Name class not found");

    ResponseStatus ERROR = new ResponseStatus(4, "ERROR");

}
