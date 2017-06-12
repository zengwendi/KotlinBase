package com.goat.kotlinbase.http.service

import com.goat.kotlinbase.bean.GankIoDataBean

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by quantan.liu on 2017/3/22.
 */

interface GankIoService {
    companion object {
        val API_GANKIO = "http://gank.io/api/"
    }

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    fun getGankIoData(@Path("type") id: String, @Path("page") page: Int, @Path("pre_page") pre_page: Int): Observable<GankIoDataBean>

}
