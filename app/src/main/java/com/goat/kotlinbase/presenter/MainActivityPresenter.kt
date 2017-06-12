package com.goat.kotlinbase.presenter

import com.goat.kotlinbase.bean.GankIoDataBean
import com.goat.kotlinbase.presenter.base.BaseView

/**
 * Created by zengwendi on 2017/6/12.
 */

interface MainActivityPresenter {
    interface View : BaseView<List<GankIoDataBean.ResultBean>>

    interface Presenter {
        fun fetchGankIoData(page: Int, pre_page: Int)
    }
}


