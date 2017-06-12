package com.goat.kotlinbase.ui.activity.main

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.bindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.goat.kotlinbase.R
import com.goat.kotlinbase.adapter.GankIoAndroidAdapter
import com.goat.kotlinbase.bean.GankIoDataBean
import com.goat.kotlinbase.presenter.MainActivityPresenter
import com.goat.kotlinbase.presenter.impl.MainActivityPresenterImpl
import com.goat.kotlinbase.ui.activity.base.BaseActivity

/**
 * Created by zengwendi on 2017/6/12.
 */

class MainActivity(override val layoutId: Int = R.layout.activity_main) : BaseActivity<MainActivityPresenterImpl>(), MainActivityPresenter.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    private var page: Int = 0
    private val PRE_PAGE = 10
    val mRecyclerView: RecyclerView by bindView(R.id.recycler_view)
    val mRefresh: SwipeRefreshLayout by bindView(R.id.refresh)
    private var isRefresh = false
    val mAdapter: GankIoAndroidAdapter = GankIoAndroidAdapter(ArrayList());

    override fun refreshView(data: List<GankIoDataBean.ResultBean>) {
        if (isRefresh) {
            mRefresh!!.isRefreshing = false
            mAdapter.setEnableLoadMore(true)
            isRefresh = false
            mAdapter.setNewData(data)
        } else {
            mRefresh!!.isEnabled = true
            page++
            mAdapter.addData(data)
            mAdapter.loadMoreComplete()
        }
    }

    override fun initData() {
        mPresenter = MainActivityPresenterImpl();
    }

    override fun loadData() {
        mPresenter!!.fetchGankIoData(page, PRE_PAGE)
    }

    override fun initView() {
//        mRefresh!!.setColorSchemeColors(resources.getColor(R.color.colorTheme))
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.adapter = mAdapter
        mRefresh!!.setOnRefreshListener(this)
//        mAdapter.setLoadMoreView(EasyLoadMoreView())
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadMoreRequested() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}



