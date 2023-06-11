package com.example.data.network.interceptor

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class MockInterceptor @Inject constructor(@ApplicationContext val mContext: Context) : Interceptor {

        companion object {
            private val JSON_MEDIA_TYPE = ("application/json").toMediaType()
            private const val MOCK = "mock"
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val path: String = request.url.encodedPath
            val filename = request.url.pathSegments.last()
            val stream: InputStream = mContext.assets?.open("mocks/$filename.json")!!

            val json: String = parseStream(stream)

            val header = request.header(MOCK)

            if (header != null) {
                return Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("")
                    .code(200)
                    .body(ResponseBody.create(JSON_MEDIA_TYPE, json ))
                    .build()
            }

            return chain.proceed(request.newBuilder().removeHeader(MOCK).build())
        }


    @Throws(IOException::class)
    private fun parseStream(stream: InputStream): String {
        val builder = java.lang.StringBuilder()
        val index = BufferedReader(InputStreamReader(stream, "UTF-8"))
        var line: String?
        while (index.readLine().also { line = it } != null) {
            builder.append(line)
        }
        index.close()
        return builder.toString()
    }
    }
