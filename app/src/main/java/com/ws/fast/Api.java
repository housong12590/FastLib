package com.ws.fast;

import com.ws.fastlib.network.Result;
import com.ws.fastlib.network.annotation.Service;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Service(baseUrl = "http://192.168.10.21:8080",printLog = true)
public interface Api {

    @GET("draft/list")
    Single<Result<DraftResult>> getDraftList(@Query("tag_id") String tagId,
                                             @Query("page") String page,
                                             @Query("page_size") String pageSize);
}
