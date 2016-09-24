package com.github.zeng1990java.retrofit2.adapter.bolts.sample.api;

import com.github.zeng1990java.retrofit2.adapter.bolts.sample.model.GankList;

import bolts.Task;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2016/9/24.
 */
public interface GankApi {

    String BASE_API = "http://gank.io/";

    @GET("api/data/Android/10/1")
    Task<GankList> getGankList();

    @GET("api/data/Android/15/1")
    Task<Response<GankList>> getGankListResponse();
}
