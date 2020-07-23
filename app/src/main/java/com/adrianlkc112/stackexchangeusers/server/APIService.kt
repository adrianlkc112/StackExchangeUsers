package com.adrianlkc112.stackexchangeusers.server

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2/")
        .client(MyOkHttpClient.getMyOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    val api: StackExchangeAPI = retrofit().create(StackExchangeAPI::class.java)
}