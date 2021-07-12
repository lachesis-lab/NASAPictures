package ru.lachesis.nasapictures.model

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.lachesis.nasapictures.BuildConfig
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*

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

    fun getRetrofitData(date: java.util.Calendar, apikey:String, callback: Callback<PictureData>){
        val dateFormat= java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        pictureData.getPictureData(dateFormat.format(date.time),apikey).enqueue(callback)
    }


}