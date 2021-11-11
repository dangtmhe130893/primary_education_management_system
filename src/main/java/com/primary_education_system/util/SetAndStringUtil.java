package com.primary_education_system.util;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SetAndStringUtil {

    private static String convertCollectionToString(Set<Long> list) {
        return list.stream().map(Object::toString)
                .collect(Collectors.joining(","));
    }

    public static Set<Long> convertStringToCollection(String strOfList) {
        Set<Long> set = new HashSet<>();
        if (strOfList == null || "".equals(strOfList)) {
            return set;
        }

        for (String str : strOfList.split(",")) {
            set.add(Long.valueOf(str));
        }
        return set;
    }

    public static String add(Long element, String currentStr) {
        if (currentStr == null) {
            return String.valueOf(element);
        }
        Set<Long> setResult = convertStringToCollection(currentStr);
        if (setResult.add(element)) {
            return convertCollectionToString(setResult);
        } else {
            return currentStr;
        }
    }
}

