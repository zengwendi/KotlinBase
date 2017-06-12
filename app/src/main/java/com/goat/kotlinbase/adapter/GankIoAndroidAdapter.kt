package com.goat.kotlinbase.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.goat.kotlinbase.R
import com.goat.kotlinbase.bean.GankIoDataBean

/**
 * Created by quantan.liu on 2017/3/30.
 */

class GankIoAndroidAdapter(data: List<GankIoDataBean.ResultBean>) : BaseQuickAdapter<GankIoDataBean.ResultBean, BaseViewHolder>(R.layout.item_wechat, data) {

    override fun convert(helper: BaseViewHolder, item: GankIoDataBean.ResultBean) {
        val ivAndroidPic = helper.getView<ImageView>(R.id.iv_android_pic)
        // 显示gif图片会很耗内存
        if (item.images != null && item.images.size > 0 && !TextUtils.isEmpty(item.images[0])) {
            ivAndroidPic.visibility = View.VISIBLE
            Glide.with(ivAndroidPic.context).load(item.images[0]).crossFade(500).into(ivAndroidPic)
        } else {
            ivAndroidPic.visibility = View.GONE
        }
        helper.setText(R.id.tv_android_des, item.desc)
        helper.setText(R.id.tv_android_who, item.who)
        helper.setText(R.id.tv_android_time, item.publishedAt)

    }
}
