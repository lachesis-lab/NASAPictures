package ru.lachesis.nasapictures.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.lachesis.nasapictures.BuildConfig

class RetrofitDataSource {
    private val baseUrl = "https://api.nasa.gov/"

    private val pictureData = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        ))
        .build().create(NASAApi::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun getRetrofitData(apikey:String,callback: Callback<PictureData>){
        pictureData.getPictureData(apikey).enqueue(callback)
    }


}