package com.example.mybasicapplication.di

import android.content.Context
import com.example.data.api.UserApi
import com.example.data.network.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ApiServiceModule {

    /*   @IntoMap
       @Provides
       fun provideResponseParseInterceptor(): Interceptor = ApiResultParserInterceptor()


        @IntoMap
        @Provides
        fun provideMockResponseParseInterceptor(): Interceptor = MockInterceptor()*/

    @Provides
    fun provideRetrofit(@ApplicationContext appContext: Context): Retrofit {

        return RetrofitHelper.createRetrofit(baseUrl = "https://api.github.com", appContext)
    }


    @Provides
    fun provideUserService(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)


}