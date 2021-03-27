package com.ben.libcom.net.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 具体的网络访问代理商实现，这里使用了OkHttp
 *
 * @author: BD
 */
@Singleton
public class OkHttpProcessor implements IHttpProcessor {

    private OkHttpClient mOkHttpClient;

    private Handler mHandler;

    @Inject
    public OkHttpProcessor(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
        mHandler = new Handler(Looper.getMainLooper());
    }

    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
            return body.build();
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            body.add(entry.getKey(), entry.getValue().toString());
        }
        return body.build();
    }

    @Override
    public void post(String url, Map<String, Object> params, final ICallback callback) {
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("BEN_DENG", "onFailure: ", e);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String result = response.body().string();
                                    Log.i("BEN_DENG", "onResponse: " + result);
                                    if (response.isSuccessful()) {
                                        callback.onSuccess(result);
                                    } else {
                                        callback.onFailure(result);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    callback.onFailure(e.getMessage());
                                }
                            }
                        });
                    }
                });
    }

}














