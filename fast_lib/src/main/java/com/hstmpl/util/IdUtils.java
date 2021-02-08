package com.hstmpl.util;


import com.hstmpl.common.SnowFlake;

import java.util.UUID;

public class IdUtils {

    private static SnowFlake snowFlake = new SnowFlake(10, 1);

    public static void initSnowFlake(int dataCenterId, int machineId) {
        snowFlake = new SnowFlake(dataCenterId, machineId);
    }

    public static Long nextId() {
        return snowFlake.nextId();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }
}
