package com.ben.appcleaner.cleaner.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.ben.appcleaner.bean.UserInfoBean
import com.ben.appcleaner.cleaner.respository.CleanerRepository
import com.ben.libcom.base.BaseViewModel
import com.ben.libcom.net.http.HttpCallback
import java.util.*

/**
 *
 * @author: BD
 */
class CleanerViewModel @ViewModelInject constructor(private val cleanerRepository: CleanerRepository) :
    BaseViewModel() {

    private var userInfoData: MutableLiveData<UserInfoBean> = MutableLiveData()

    fun refreshData() {
        val params: MutableMap<String, String> =
            HashMap()
        params["username"] = "xxx"
        params["password"] = "123456"
        cleanerRepository.getUserInfo(params, compositeDisposable,
            object : HttpCallback<UserInfoBean?>() {
                override fun onSuccess(userInfoBean: UserInfoBean?) {
                    userInfoData.value = userInfoBean
                }

                override fun onFailure(e: String?) {}
            })
    }
}