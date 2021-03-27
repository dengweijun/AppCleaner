package com.ben.appcleaner

import androidx.startup.AppInitializer
import com.ben.libcom.base.BaseApplication
import com.ben.libcom.global.AppConfig
import dagger.hilt.android.HiltAndroidApp

/**
 * App入口
 *
 * @author: BD
 */
@HiltAndroidApp
class CleanApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(this)
            .initializeComponent(AppConfig::class.java)
            .baseUrl("https://www.baidu.com")
            .connTimeout(5000)
            .readTimeout(5000)
            .writeTimeout(5000)
            .configure()
    }
}