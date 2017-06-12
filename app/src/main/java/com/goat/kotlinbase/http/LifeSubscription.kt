package com.goat.kotlinbase.http

import io.reactivex.disposables.Disposable

/**
 * Created by zengwendi on 2017/6/12.
 */
interface LifeSubscription {
    fun bindSubscription(disposable: Disposable)
}