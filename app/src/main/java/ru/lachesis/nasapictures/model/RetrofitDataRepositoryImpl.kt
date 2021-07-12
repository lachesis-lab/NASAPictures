package ru.lachesis.nasapictures.model

import retrofit2.Callback
import java.util.*

class RetrofitDataRepositoryImpl(private val dataSource: RetrofitDataSource): RemoteDataRepository  {
    override fun getRemoteData(date:Calendar, apikey:String,callback: Callback<PictureData>) {
        dataSource.getRetrofitData(date,apikey,callback)
    }
}