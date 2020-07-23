package com.adrianlkc112.stackexchangeusers.server

import com.adrianlkc112.stackexchangeusers.model.UserItems
import io.reactivex.Observable
import retrofit2.http.*

interface StackExchangeAPI {

    @GET("users")
    fun getUsers(@Query("order") order: String = "desc",
                 @Query("sort") sort: String = "name",                      //sort by name
                 @Query("site") site: String = "stackoverflow",
                 @Query("max") max: Int = 20,                               //get first 20 data only
                 @Query("inname") inname: String)                           //filter the results down to just those users with a certain substring in their display name
            : Observable<UserItems>
}