package com.adrianlkc112.stackexchangeusers.server

import com.adrianlkc112.stackexchangeusers.BuildConfig
import com.cityline.server.ssl.Tls12SocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MyOkHttpClient {
    companion object {
        fun getMyOkHttpClient(): OkHttpClient {
            try {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY         //Enable this code only for debugging

                val builder = OkHttpClient.Builder().apply {
                    if(BuildConfig.BUILD_TYPE == "debug") {
                        //readTimeout(60, TimeUnit.SECONDS)
                        //connectTimeout(60, TimeUnit.SECONDS)
                        addInterceptor(logging)
                    }
                }
                return (Tls12SocketFactory.enableTls12(builder)).build()

            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}