package com.goat.kotlinbase.http.utils

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.goat.kotlinbase.app.Constants
import com.goat.kotlinbase.bean.GankIoDataBean
import com.goat.kotlinbase.http.LifeSubscription
import com.goat.kotlinbase.http.Stateful
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

/**
 * Created by zengwendi on 2017/6/12.
 *  为网络请求提供一个主构造方法进行结果回调处理
 */
class Callback<T>(val mListener: HttpOnNextListener<Any?>) : Observer<T> {
    var mLifeSubscription: LifeSubscription? = null
    var mTarget: Stateful? = null

    fun detachView() {
        if (mTarget != null) {
            mTarget = null
        }
    }

    fun setLifeSubscription(lifecycle: LifeSubscription) {
        mLifeSubscription = lifecycle
    }

    override fun onNext(value: T) {
        if (value is GankIoDataBean) {
            onResponse(value)
        }
    }


    //        添加Disposable防止内存泄露
    override fun onSubscribe(d: Disposable?) {
        mLifeSubscription!!.bindSubscription(d!!)
    }

    override fun onError(e: Throwable?) {
    }

    override fun onComplete() {
    }

    /**
     * 统一处理成功回掉
     */
    fun onResponse(data: GankIoDataBean) {
        if (data == null) {
            onfail(Throwable())
            mListener.onError()
            return
        }
        if (data!!.isError) {
            ToastUtils.showShort("请求失败")
            mListener.onError()
            return
        }
        if (mTarget != null)
            mTarget!!.setState(Constants.STATE_SUCCESS)
        mListener!!.onNext(data.results!!)
    }

    fun onfail(e: Throwable) {
        mListener.onError()
        if (!NetworkUtils.isAvailableByPing()) {
            ToastUtils.showShort("你连接的网络有问题，请检查网络连接状态")
            if (mTarget != null) {
                mTarget!!.setState(Constants.STATE_ERROR)
            }
            return
        }
        if (e is HttpException) {
            mTarget!!.setState(Constants.STATE_ERROR)
            ToastUtils.showShort("服务器异常")
            return
        }
        mTarget!!.setState(Constants.STATE_ERROR)
        ToastUtils.showShort("数据异常")
    }
}