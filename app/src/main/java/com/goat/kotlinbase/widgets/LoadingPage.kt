package com.goat.kotlinbase.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.goat.kotlinbase.R
import com.goat.kotlinbase.app.Constants
import com.goat.kotlinbase.app.Constants.Companion.STATE_EMPTY
import com.goat.kotlinbase.app.Constants.Companion.STATE_LOADING
import com.goat.kotlinbase.app.Constants.Companion.STATE_SUCCESS

/**
 * Created by zengwendi on 2017/6/12.
 */

abstract class LoadingPage(val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(mContext, attrs, defStyleAttr) {
    private var loadingView: View? = null                 // 加载中的界面
    private var errorView: View? = null                   // 错误界面
    private var emptyView: View? = null                   // 空界面
    var contentView: View? = null                 // 加载成功的界面


    var state = Constants.STATE_UNKNOWN

    init {
        init();
    }

    fun init() {
        //把loadingView添加到frameLayout上
        if (loadingView == null) {
            loadingView = createLoadingView()
            this.addView(loadingView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
        //把emptyView添加到frameLayout上
        if (emptyView == null) {
            emptyView = createEmptyView()
            this.addView(emptyView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        }
        //把errorView添加到frameLayout上
        if (errorView == null) {
            errorView = createErrorView()
            this.addView(errorView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
        showPage()//根据状态显示界面
    }

    private fun createLoadingView(): View? {
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.layout_state_loading, null)
        return loadingView
    }

    private fun createEmptyView(): View? {
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_state_empty, null)
        return emptyView
    }

    private fun createErrorView(): View? {
        errorView = LayoutInflater.from(mContext).inflate(R.layout.layout_state_error, null)
        errorView!!.setOnClickListener({
            state = STATE_LOADING
            showPage()
            loadData()
        })
        return errorView
    }

    fun showPage() {
        if (loadingView != null) {
            if (state == Constants.STATE_UNKNOWN || state == Constants.STATE_LOADING) {
                loadingView!!.visibility = View.VISIBLE
                // 开始动画
            } else {
                loadingView!!.visibility = View.GONE
            }
            if (state == STATE_EMPTY || state == Constants.STATE_ERROR || state == Constants.STATE_SUCCESS) {
                loadingView!!.visibility = View.GONE
            }
        }

        if (emptyView != null) {
            emptyView!!.visibility = if (state == Constants.STATE_EMPTY) View.VISIBLE else View.GONE
        }

        if (errorView != null) {
            errorView!!.visibility = if (state == Constants.STATE_ERROR) View.VISIBLE else View.GONE
        }

        if (state == STATE_SUCCESS) {
            if (contentView == null) {
                contentView = LayoutInflater.from(mContext).inflate(getLayoutId(), null)
                addView(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                initView()
            }
            contentView!!.visibility = View.VISIBLE
        } else {
            if (contentView!=null){
                contentView!!.visibility = View.GONE
            }
        }
    }

    /**
     * 子类关于View的操作(如setAdapter)都必须在这里面，会因为页面状态不为成功，而binding还没创建就引用而导致空指针。
     */
    abstract fun initView()

    /**
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    abstract fun loadData()


    abstract fun getLayoutId(): Int
}
