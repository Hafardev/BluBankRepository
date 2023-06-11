package com.example.data.network

import android.annotation.SuppressLint
import android.content.Context
import com.example.data.network.interceptor.MockInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitHelper {


    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor()

        init {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        @SuppressLint("SuspiciousIndentation")
        fun createRetrofit(
            baseUrl: String,
            mContext: Context
        ): Retrofit {
            val mMockInterceptor = MockInterceptor(mContext)

            val mClient = OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(mMockInterceptor)
                .build()

            return Retrofit
                .Builder()
                .client(mClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


        private fun createClient(
            timeOutInSecond: Long,
            interceptors: Map<Int, Interceptor>,
            headers: Map<String, String>,
            certPinner: CertificatePinner?
        ): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.writeTimeout(timeOutInSecond, TimeUnit.SECONDS)
                .readTimeout(timeOutInSecond, TimeUnit.SECONDS)
                .connectTimeout(timeOutInSecond, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(0, 5, TimeUnit.SECONDS))
                .retryOnConnectionFailure(true)

            if (interceptors.isNotEmpty()) {
                val treeMap: TreeMap<Int, Interceptor> = TreeMap<Int, Interceptor>(interceptors)
                for (interceptor in treeMap.values) {
                    okHttpClientBuilder.addInterceptor(interceptor)
                }
            }
            if (headers.isNotEmpty()) {
                okHttpClientBuilder.addInterceptor { chain: Interceptor.Chain ->
                    val req = chain.request().newBuilder()
                    for ((key, value) in headers.entries) {
                        req.addHeader(key, value)
                    }
                    chain.proceed(req.build())
                }
            }
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            certPinner?.let {
                okHttpClientBuilder.certificatePinner(it)
            }
            return okHttpClientBuilder.build()
        }
    }
}