package br.com.camargo.hotel.management.commons.util;

import org.apache.commons.lang3.StringUtils;

public final class SqlUtils {
    public static String contains(String value) {
        return "%" + StringUtils.defaultIfBlank(value, "") + "%";
    }
}
