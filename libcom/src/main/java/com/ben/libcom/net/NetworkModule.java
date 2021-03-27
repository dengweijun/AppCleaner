package com.ben.libcom.net;

import android.util.Log;

import com.ben.libcom.global.AppConfig;
import com.ben.libcom.global.ConfigKeys;
import com.ben.libcom.net.http.IHttpProcessor;
import com.ben.libcom.net.http.OkHttpProcessor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络库相关类的初始化操作
 *
 * @author: BD
 */
@Module
@InstallIn(ApplicationComponent.class)
public abstract class NetworkModule {

    @Binds
    public abstract IHttpProcessor bindHttpProcessor(OkHttpProcessor httpProcessor);

    @Provides
    static AppConfig provideAppConfig() {
        return AppConfig.getInstance();
    }

    // HttpLoggingInterceptor
    @Provides
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("BEN_DENG", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    // Interceptor
    @Provides
    static Interceptor provideInterceptor() {
        Interceptor interceptor = chain -> {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .header("XX-Device-Type", "android");
            Request compressedRequest = builder.build();
            return chain.proceed(compressedRequest);
        };
        return interceptor;
    }

    // OkHttpClient
    @Provides
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                            Interceptor okHttpInterceptor,
                                            AppConfig appConfig) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(okHttpInterceptor)
                .connectTimeout(appConfig.getConfiguration(ConfigKeys.CONN_TIMEOUT), TimeUnit.SECONDS)
                .writeTimeout(appConfig.getConfiguration(ConfigKeys.WRITE_TIMEOUT), TimeUnit.SECONDS)
                .readTimeout(appConfig.getConfiguration(ConfigKeys.READ_TIMEOUT), TimeUnit.SECONDS)
                .build();
    }

    // Retrofit
    @Singleton
    @Provides
    static Retrofit provideDefaultRetrofit(Retrofit.Builder builder, AppConfig appConfig) {
        return builder
                .baseUrl(appConfig.<String>getConfiguration(ConfigKeys.BASE_URL))
                .build();
    }

    // Retrofit.Builder
    @Provides
    static Retrofit.Builder provideRetrofitBuilder(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);
    }


}
