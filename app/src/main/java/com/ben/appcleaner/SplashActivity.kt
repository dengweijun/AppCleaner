package com.ben.appcleaner

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.ben.appcleaner.databinding.ActivitySplashBinding
import com.ben.libcom.base.BaseNormalActivity
import com.ben.libcom.utils.CommonToast
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint


/**
 * 页面：启动
 * @author: BD
 */
class SplashActivity : BaseNormalActivity() {

    /**
     * 权限组
     */
    private val permissionsGroup = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
    }

    @SuppressLint("CheckResult")
    private fun requestPermission() {
        RxPermissions(this).request(*permissionsGroup).subscribe { result ->
            if (result) {
                binding.clRoot.postDelayed({
                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )
                }, 1500)
            } else {
                CommonToast.showShortToast(this, "未授权")
            }
        }
    }
}