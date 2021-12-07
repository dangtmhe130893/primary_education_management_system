package com.primary_education_system.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constant {

    public static final String SYSTEM_ADMIN = "SYSTEM_ADMIN";
    public static final String COMPANY_ADMIN = "COMPANY_ADMIN";

    public static final String TEACHER = "TEACHER";

    public static final Map<String, Integer> mapGender = Stream.of(new Object[][]{
            {"Nam", 1},
            {"Nữ", 2},
            {"Khác", 9},
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

    public static Set<String> setGrade = Stream.of("Khối 1", "Khối 2", "Khối 3", "Khối 4", "Khối 5")
            .collect(Collectors.toCollection(HashSet::new));
}
