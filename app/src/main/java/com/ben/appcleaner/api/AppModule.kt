package com.ben.appcleaner.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit

/**
 * 初始化所有的Service接口
 * @author: BD
 */
@Module
@InstallIn(ActivityComponent::class)
class AppModule {

    @ActivityScoped
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService? {
        return retrofit.create(ApiService::class.java)
    }
}