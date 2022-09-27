package com.accountant.service.accountant.enums;

import java.util.HashMap;
import java.util.Map;

public enum IsoCodeEnum {
    USD(0, "USD"),
    GEL(1, "GEL");

    private static final Map<Integer, IsoCodeEnum> codeMap = new HashMap<>();

    static {
        for (IsoCodeEnum s : IsoCodeEnum.values()) {
            codeMap.put(s.getCode(), s);
        }
    }

    private final int code;
    private final String name;

    IsoCodeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static IsoCodeEnum getById(int id) {
        return codeMap.get(id);
    }

}
