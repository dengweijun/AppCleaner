package com.ben.libcom.net.retrofit.Exception;

import android.util.Log;

import io.reactivex.functions.Consumer;

/**
 * 通用处理异常回调的Consumer
 *
 * @author: BD
 */
public abstract class ErrorConsumer implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        ApiException ex;
        if (throwable instanceof ApiException) {
            ex = (ApiException) throwable;
        } else {
            ex = ApiException.handleException(throwable);
        }
        error(ex);
        Log.e("BEN_DENG", "error: ", throwable);
    }

    protected abstract void error(ApiException e);

}