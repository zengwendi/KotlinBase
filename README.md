# 用kotlin搭建的Android框架
## Overview
本项目用kotlin基于Rxjava2 + Retrofit2  + MVP构架搭建的Android框架，其中包含kotlin一些基本语法的使用、kotlin单例的实现、kotlin泛型的使用等，其中对BaseActivity的封装，对Retrofit2请求结果的统一处理、RecyclerView实现上拉加载下拉刷新等。对与想学习kotlin的同学可以下载下来玩玩看，后期还会继续更新。

## Screenshots
包的结构  
![](http://i.imgur.com/ygwgKQy.png)  
应用截图  
![](http://i.imgur.com/VvT1wM4.png)  
暂时只写了一个页面，后期更新。  
## Resources
kotlin的官方github地址了：  
[https://github.com/JetBrains/kotlin](https://github.com/JetBrains/kotlin)  
github trending，会有很多的新新优质资源：  
[https://github.com/trending/java](https://github.com/trending/java)  
官方中文翻译：  
[https://www.kotlincn.net/docs/reference/](https://www.kotlincn.net/docs/reference/)
## Introduction     
### kotlin的配置     
1.在项目的build.gradle中
	
	buildscript {
    ext.kotlin_version = '1.1.2-4'
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.0'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
2.module的build.gradle中
	
	apply plugin: 'com.android.application'
	apply plugin: 'kotlin-android'

	android {
    ...
	

	dependencies {
    ...
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
	}
这样就配置完了当前module已经支持kotlin语言了  
其实还有更简单的办法配置：  
1.使用Android Studio下载Kotlin相关插件  
![](http://i.imgur.com/yYw9NlO.png)
2.随便点击一个Java文件使用快捷键ctrl+alt+shift+k键把java文件转换成.kt文件  
3.如果没有项目没有配置kotlin支持的话右上角会弹出  
![](http://i.imgur.com/Vn6thbb.png)
4.点击Configure就点ok就自动配置好了  
![](http://i.imgur.com/u1HkpO9.png)  
配置跟手动配置的文件一样的。
### kotlin单例
思路跟java一样，这里是对retrofit进行了单例处理，直接上代码
	
	/**
     * Created by zengwendi on 2017/6/12.
     * apiManager实现单列模式
     */
    //主构造方法私有化
    class ApiManager private constructor() {
    private var mService: GankIoService? = null

    companion object {
        private var mApiManager: ApiManager? = null

        val instence: ApiManager
            get() {
                if (mApiManager == null) {
                    synchronized(ApiManager::class) {
                        if (mApiManager == null) {
                            mApiManager = ApiManager()
                        }
                    }
                }
                return mApiManager!!
            }
    }

    val service: GankIoService get() {
        if (mService == null) {
            val retrofit = Retrofit.Builder()
                    .client(getClient(OkHttpClient.Builder()))
                    .baseUrl(GankIoService.API_GANKIO)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService = retrofit.create<GankIoService>(GankIoService::class.java)
        }
        return mService!!
    }

    //OkHttpClient 赋值
    private fun getClient(builder: OkHttpClient.Builder): OkHttpClient {
        builder.hostnameVerifier { _, _ -> true }
        //设置超时
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS)
        builder.writeTimeout(10000, TimeUnit.MILLISECONDS)
        builder.readTimeout(10000, TimeUnit.MILLISECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        return builder.build()
    }

### kotlin中使用kotterknife	
kotterknife就是java中的ButterKnife--View注入框架。  
也是由原作者JakeWharton所写  
能够在Activity, Dialog, ViewGroup, Fragment和and recycler view's ViewHolder中使用    
地址：[https://github.com/JakeWharton/kotterknife](https://github.com/JakeWharton/kotterknife)  
1.添加依赖	
   
	compile 'com.jakewharton:kotterknife:0.1.0-SNAPSHOT'
2.使用
	
	val mRecyclerView: RecyclerView by bindView(R.id.recycler_view)
    val mRefresh: SwipeRefreshLayout by bindView(R.id.refresh)

非常简单这就完成了RecyclerView与SwipeRefreshLayout的注入。

## Thanks
[易读](https://github.com/laotan7237/EasyReader)非常感谢作者，此项目的目录结构以及MVP的架构思想从该项目中获取。API也是用了改项目的干货集中营API，作者对我的帮助很大。