package ru.lachesis.nasapictures.model

import retrofit2.Callback
import java.util.*

interface RemoteDataRepository {
    fun getRemoteData(date: Calendar, apikey:String,callback: Callback<PictureData>)
}