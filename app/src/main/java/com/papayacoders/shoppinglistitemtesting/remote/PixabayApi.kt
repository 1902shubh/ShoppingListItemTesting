package com.papayacoders.shoppinglistitemtesting.remote

import com.papayacoders.shoppinglistitemtesting.BuildConfig
import com.papayacoders.shoppinglistitemtesting.remote.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery : String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ) : Response<ImageResponse>

}