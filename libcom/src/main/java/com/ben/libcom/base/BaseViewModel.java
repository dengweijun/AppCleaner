package com.ben.libcom.base;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * ViewModel基类：控制ViewModel的生命周期，不工作时，终端网络请求
 *
 * @author: BD
 */
public class BaseViewModel extends ViewModel {

    protected final CompositeDisposable compositeDisposable;

    public BaseViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("BEN_DENG", "onCleared: ");
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
