package com.ws.fastlib.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPools {

    private static ExecutorService es;

    public static void execute(Runnable runnable) {
        getExecutorService().execute(runnable);
    }

    private static ExecutorService getExecutorService() {
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
