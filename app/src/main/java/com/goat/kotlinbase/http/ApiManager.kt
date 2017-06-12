package com.goat.kotlinbase.http

import com.goat.kotlinbase.http.service.GankIoService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by zengwendi on 2017/6/12.
 * apiManager实现单列模式
 */
//主构造方法私有化
class ApiManager private constructor() {
    private var mService: GankIoService? = null

    companion object {
        private var mApiManager: ApiManager? = null

        val instence: ApiManager
            get() {
                if (mApiManager == null) {
                    synchronized(ApiManager::class) {
                        if (mApiManager == null) {
                            mApiManager = ApiManager()
                        }
                    }
                }
                return mApiManager!!
            }
    }

    val service: GankIoService get() {
        if (mService == null) {
            val retrofit = Retrofit.Builder()
                    .client(getClient(OkHttpClient.Builder()))
                    .baseUrl(GankIoService.API_GANKIO)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService = retrofit.create<GankIoService>(GankIoService::class.java)
        }
        return mService!!
    }

    //OkHttpClient 赋值
    private fun getClient(builder: OkHttpClient.Builder): OkHttpClient {
        builder.hostnameVerifier { _, _ -> true }
        //设置超时
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS)
        builder.writeTimeout(10000, TimeUnit.MILLISECONDS)
        builder.readTimeout(10000, TimeUnit.MILLISECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        return builder.build()
    }
}