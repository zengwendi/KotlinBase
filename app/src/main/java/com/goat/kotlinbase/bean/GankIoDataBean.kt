package com.goat.kotlinbase.bean


import java.io.Serializable

/**
 * Created by jingbin on 2016/11/24.
 */

data class GankIoDataBean constructor(val isError: Boolean = false, val results: List<ResultBean>? = null) : Serializable {

    data class ResultBean(val _id: String? = null,
                          val createdAt: String? = null,
                          val desc: String? = null,
                          val publishedAt: String? = null,
                          val source: String? = null,
                          val type: String? = null,
                          val url: String? = null,
                          val isUsed: Boolean = false,
                          val who: String? = null,
                          val images: List<String>? = null) : Serializable {

    }
}
