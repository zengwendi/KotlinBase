package com.goat.kotlinbase.http.utils

/**
 * Created by zengwendi on 2017/6/12.
 */
abstract class HttpOnNextListener<out T> {
    val t: T? = null
    /**
     * 成功后回调方法
     * @param t
     */
    abstract fun onNext(t: Any)

    /**
     * 緩存回調結果
     * @param cache
     */
    fun onCacheNext(cache: String) {

    }

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     */
    fun onError() {

    }

    /**
     * 取消回調
     */
    fun onCancel() {

    }
}