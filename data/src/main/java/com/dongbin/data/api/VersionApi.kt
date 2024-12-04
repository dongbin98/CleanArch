package com.dongbin.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VersionApi {
    @GET("referral-by-code")
    suspend fun fetchReferral(@Query("code") code: String): Response<String>
}