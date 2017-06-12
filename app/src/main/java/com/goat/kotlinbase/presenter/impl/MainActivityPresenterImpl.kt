package com.goat.kotlinbase.presenter.impl

import com.goat.kotlinbase.bean.GankIoDataBean
import com.goat.kotlinbase.http.ApiManager
import com.goat.kotlinbase.http.utils.Callback
import com.goat.kotlinbase.http.utils.HttpOnNextListener
import com.goat.kotlinbase.presenter.MainActivityPresenter
import shinetechzz.com.vcleaders.presenter.base.BasePresenter

/**
 * Created by zengwendi on 2017/6/12.
 */
class MainActivityPresenterImpl : BasePresenter<MainActivityPresenter.View>(), MainActivityPresenter.Presenter {
    override fun fetchGankIoData(page: Int, pre_page: Int) {
        val listener = object : HttpOnNextListener<List<GankIoDataBean.ResultBean>>() {
            override fun onNext(t: Any) {
                mView!!.refreshView(t as List<GankIoDataBean.ResultBean>)
            }
        }
        invoke(ApiManager.instence.service.getGankIoData("Android", page, pre_page), Callback(listener))
    }

}