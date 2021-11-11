package com.primary_education_system.util;

import java.util.List;

public class ArrayUtils {

    public static String[] convertArrayListToArray(List<String> list) {
        String[] array = new String[list.size()];
        return list.toArray(array);
    }

}
