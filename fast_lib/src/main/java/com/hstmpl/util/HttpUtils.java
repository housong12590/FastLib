package com.hstmpl.util;

import com.blankj.utilcode.util.FileIOUtils;
import com.hstmpl.convert.Convert;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtils {

    private static OkHttpClient httpClient;
    private static final Long DEFAULT_TIMEOUT = 60L;
    private static final int REQUEST_OK = 200;

    private static OkHttpClient defaultClient() {
        return new OkHttpClient.Builder()
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public static void setHttpClient(OkHttpClient httpClient) {
        if (httpClient != null) {
            HttpUtils.httpClient = httpClient;
        }
    }

    public static Response doRequest(Request request) {
        try {
            if (httpClient == null) {
                synchronized (HttpUtils.class) {
                    if (httpClient == null) {
                        httpClient = defaultClient();
                    }
                }
            }
            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T doRequest(Request request, Class<T> entityCls) {
        Response response = doRequest(request);
        if (REQUEST_OK == response.code() && response.isSuccessful()) {
            if (entityCls == Response.class) {
                return (T) response;
            } else if (entityCls == ResponseBody.class) {
                return (T) response.body();
            }
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }
            try {
                if (entityCls == String.class) {
                    return (T) body.string();
                }
                return JSON.toBean(body.string(), entityCls);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("response code " + response.code());
    }

    public static <T> T doGet(String url, Object data, Class<T> entityCls) {
        return doGet(url, null, data, entityCls);
    }

    public static <T> T doGet(String url, Map<?, ?> header, Object data, Class<T> entityCls) {
        if (data != null) {
            if (!(data instanceof Map)) {
                data = BeanUtils.toMap(data);
            }
            Map<?, ?> temp = (Map<?, ?>) data;
            url += "?" + StringUtils.toQueryString(temp);
        }
        Request.Builder builder = new Request.Builder().url(url);
        addHeader(builder, header);
        Request request = builder.build();
        return doRequest(request, entityCls);
    }

    public static <T> T doPost(String url, Object data, Class<T> entityCls) {
        return doPost(url, null, data, entityCls);
    }

    public static <T> T doPost(String url, Map<?, ?> header, Object data, Class<T> entityCls) {
        String payload = data == null ? "" : JSON.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), payload);
        Request.Builder builder = new Request.Builder().url(url).post(body);
        addHeader(builder, header);
        Request request = builder.build();
        return doRequest(request, entityCls);
    }

    private static void addHeader(Request.Builder builder, Map<?, ?> header) {
        if (MapUtils.isEmpty(header)) {
            return;
        }
        for (Map.Entry<?, ?> entry : header.entrySet()) {
            String k = Convert.toString(entry.getKey());
            String v = Convert.toString(entry.getValue());
            builder.addHeader(k, v);
        }
    }

    public static <T> T formPost(String url, Map<String, Object> params, Class<T> entityCls) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.add(entry.getKey(), Convert.toString(entry.getValue()));
        }
        FormBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        return doRequest(request, entityCls);
    }

    public static boolean download(String url, File outFile) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = doRequest(request);
        ResponseBody body = response.body();
        if (response.isSuccessful() && body != null) {
            FileIOUtils.writeFileFromIS(outFile, body.byteStream());
            return true;
        }
        return false;
    }
}
