package com.accountant.service.accountant.enums;

import java.util.HashMap;
import java.util.Map;

public enum SalaryEnum {
    MONTHLY(0, "MONTHLY"),
    YEARLY(1, "YEARLY");

    private static final Map<Integer, SalaryEnum> codeMap = new HashMap<>();

    static {
        for (SalaryEnum s : SalaryEnum.values()) {
            codeMap.put(s.getCode(), s);
        }
    }

    private final int code;
    private final String name;

    SalaryEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SalaryEnum getById(int id) {
        return codeMap.get(id);
    }
}
