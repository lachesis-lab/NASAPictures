package ru.lachesis.nasapictures.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NASAApi {
    @GET("planetary/apod")
    fun getPictureData(
        @Query("date") date:String,
        @Query("api_key") apikey:String
    ): Call<PictureData>
}