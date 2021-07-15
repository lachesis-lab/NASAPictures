package ru.lachesis.nasapictures.model

import retrofit2.Callback

class RetrofitDataRepositoryImpl(private val dataSource: RetrofitDataSource): RemoteDataRepository  {
    override fun getRemoteData(apikey:String,callback: Callback<PictureData>) {
        dataSource.getRetrofitData(apikey,callback)
    }
}