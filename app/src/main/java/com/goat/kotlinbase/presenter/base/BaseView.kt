package com.goat.kotlinbase.presenter.base

/**
 * Created by zengwendi on 2017/6/12.
 */
interface BaseView<in T> {
    fun refreshView(mData: T)//获取数据成功调用该方法。
}