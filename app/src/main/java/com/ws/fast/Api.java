package com.ws.fast;

import com.ws.fastlib.network.Result;
import com.ws.fastlib.network.annotation.Service;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Service(baseUrl = "https://api.ciinweb.com/bc/")
public interface Api {

    @GET("draft/list")
    Single<Result<DraftResult>> getDraftList(@Query("tag_id") String tagId,
                                             @Query("page") int page,
                                             @Query("page_size") int pageSize);
}
