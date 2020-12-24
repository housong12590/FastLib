package com.hstmpl.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPools {

    private static ExecutorService es;

    public static Future<?> submit(Runnable runnable) {
        return getExecutorService().submit(runnable);
    }

    public static ExecutorService getExecutorService() {
        if (es == null) {
            synchronized (ThreadPools.class) {
                if (es == null) {
                    es = Executors.newCachedThreadPool();
                }
            }
        }
        return es;
    }

}
