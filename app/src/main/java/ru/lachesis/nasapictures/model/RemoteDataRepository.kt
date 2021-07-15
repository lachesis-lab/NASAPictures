package ru.lachesis.nasapictures.model

import retrofit2.Callback

interface RemoteDataRepository {
    fun getRemoteData(apikey:String,callback: Callback<PictureData>)
}