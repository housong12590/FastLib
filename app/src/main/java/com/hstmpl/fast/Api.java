package com.hstmpl.fast;

import com.hstmpl.net.Result;
import com.hstmpl.net.annotation.Service;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Service(baseUrl = "https://api.ciinweb.com/xjl/")
public interface Api {

    @GET("app_bgm/list")
    Single<Result<BgmResult>> getBgmList(@Query("page") int page, @Query("page_size") int pageSize);
}
