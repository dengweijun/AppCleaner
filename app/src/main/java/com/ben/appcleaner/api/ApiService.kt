package com.ben.appcleaner.api

import com.ben.appcleaner.bean.UserInfoBean
import com.ben.libcom.net.retrofit.response.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * 网络接口
 * @author: BD
 */
interface ApiService {
    /**
     * 获取用户信息
     */
    @GET("/")
    fun getUserInfo(@QueryMap map: MutableMap<String, String>): Observable<BaseResponse<UserInfoBean?>?>?
}