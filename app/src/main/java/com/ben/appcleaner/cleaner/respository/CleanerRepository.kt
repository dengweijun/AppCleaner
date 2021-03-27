package com.ben.appcleaner.cleaner.respository

import com.ben.appcleaner.api.ApiService
import com.ben.appcleaner.bean.UserInfoBean
import com.ben.appcleaner.cleaner.data.LocalSource
import com.ben.libcom.net.http.HttpCallback
import com.ben.libcom.net.retrofit.Exception.ApiException
import com.ben.libcom.net.retrofit.Exception.ErrorConsumer
import com.ben.libcom.net.retrofit.response.ResponseTransformer
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 *
 * @author: BD
 */
@ActivityScoped
class CleanerRepository @Inject constructor(
    private var apiService: ApiService?,
    private var localSource: LocalSource?
) {

    fun getUserInfo(
        params: MutableMap<String, String>,
        compositeDisposable: CompositeDisposable?,
        callback: HttpCallback<UserInfoBean?>
    ) {
        apiService?.getUserInfo(params)
            ?.compose(ResponseTransformer.obtain(compositeDisposable))
            ?.subscribe(
                Consumer { userInfo -> callback.onSuccess(userInfo) },
                object : ErrorConsumer() {
                    override fun error(e: ApiException) {
                        callback.onFailure(e.message)
                    }
                })
    }

}