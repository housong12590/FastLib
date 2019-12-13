package com.ws.fastlib.network;


import com.ws.fastlib.network.annotation.Service;
import com.ws.fastlib.network.log.Level;
import com.ws.fastlib.network.log.LoggingInterceptor;
import com.ws.fastlib.network.proxy.SchedulersProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    private static ConcurrentHashMap<String, Retrofit> mCacheMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> clazz) {
        Retrofit retrofit;
        Service service = clazz.getAnnotation(Service.class);
        if (service == null) {
            throw new NullPointerException("API interfaces need use @Service...");
        }
        retrofit = mCacheMap.get(clazz.getName());
        if (retrofit == null) {
            OkHttpClient httpClient = getHttpClient(service);
            retrofit = new Retrofit.Builder().client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(service.baseUrl()).build();
            mCacheMap.put(clazz.getName(), retrofit);
        }
        T apiService = retrofit.create(clazz);
        if (service.schedulers()) {
            InvocationHandler handler = new SchedulersProxy(apiService);
            return (T) Proxy.newProxyInstance(apiService.getClass().getClassLoader(),
                    apiService.getClass().getInterfaces(), handler);
        }
        return apiService;
    }

    private static OkHttpClient getHttpClient(Service service) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(service.timeout(), service.unit());
        builder.connectTimeout(service.timeout(), service.unit());
        Class<?>[] interceptors = service.interceptors();
        for (Class<?> cls : interceptors) {
            try {
                Interceptor interceptor = (Interceptor) cls.newInstance();
                builder.addInterceptor(interceptor);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        if (service.printLog()) {
            LoggingInterceptor interceptor = new LoggingInterceptor.Builder().loggable(true).setLevel(Level.BASIC).build();
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    public static OkHttpClient getDefaultClient() {
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
