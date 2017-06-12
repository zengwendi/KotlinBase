package com.goat.kotlinbase.ui.activity.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.goat.kotlinbase.http.LifeSubscription
import com.goat.kotlinbase.http.Stateful
import com.goat.kotlinbase.widgets.LoadingPage
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import shinetechzz.com.vcleaders.presenter.base.BasePresenter

/**
 * Created by zengwendi on 2017/6/12.
 */

abstract class BaseActivity<T : BasePresenter<*>> : AppCompatActivity(), LifeSubscription, Stateful {
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var mPresenter: T? = null
    var mLoadingPage: LoadingPage? = null
    var bind: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        mPresenter!!.attachView(this)
        mLoadingPage = object : LoadingPage(this) {
            override fun initView() {
                bind = ButterKnife.bind(this@BaseActivity, contentView!!)
                this@BaseActivity.initView()
            }

            override fun loadData() {
                this@BaseActivity.loadData()
            }

            override fun getLayoutId(): Int {
                return this@BaseActivity.layoutId
            }
        }
        setContentView(mLoadingPage)
        loadData()
    }

    //用于监听rxjava防止内存泄露
    override fun bindSubscription(disposable: Disposable) {
        mCompositeDisposable!!.add(disposable)
    }

    override fun setState(state: Int) {
        mLoadingPage!!.state = state
        mLoadingPage!!.showPage()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
        mPresenter!!.detachView()
    }

    /**
     * ①
     * 初始化数据接收intent的数据
     * 设置ActionBar
     */
    abstract fun initData()

    /**
     * ②
     * 请求网络获取的数据返回状态
     * * 如果是静态页面不需要网络请求的在子类的loadData方法中添加以下2行即可
     * mLoadingPage.state = STATE_SUCCESS;
     * mLoadingPage.showPage();
     * 或者调用setState(AppConstants.STATE_SUCCESS)
     */
    abstract fun loadData()

    /**
     * ③
     * 网络请求成功再去加载布局
     * @return
     */
    abstract val layoutId: Int

    /**
     * ④
     * 子类关于View的操作(如setAdapter)都必须在这里面，会因为页面状态不为成功，而binding还没创建就引用而导致空指针。
     * loadData()和initView只执行一次，如果有一些请求需要二次的不要放到loadData()里面。
     */
    abstract fun initView()
}
