package com.goat.kotlinbase.app

import android.app.Application
import com.blankj.utilcode.util.Utils

/**
 * Created by zengwendi on 2017/6/12.
 */

class KotlinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}

