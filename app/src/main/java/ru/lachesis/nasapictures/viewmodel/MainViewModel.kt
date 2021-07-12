package ru.lachesis.nasapictures.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.lachesis.nasapictures.BuildConfig
import ru.lachesis.nasapictures.app.AppState
import ru.lachesis.nasapictures.model.PictureData
import ru.lachesis.nasapictures.model.RetrofitDataRepositoryImpl
import ru.lachesis.nasapictures.model.RetrofitDataSource

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitDataRepositoryImpl: RetrofitDataRepositoryImpl = RetrofitDataRepositoryImpl(
        RetrofitDataSource()),
) : ViewModel() {
    private val apikey = BuildConfig.NASA_API_KEY
    fun getLiveData() = liveDataToObserve

    fun getData(): LiveData<AppState> {
        liveDataToObserve.value = AppState.Loading(null)
        if (apikey.isBlank()) {
            AppState.Error(Error("You need API key"))
        } else {
            retrofitDataRepositoryImpl.getRemoteData(apikey, object : Callback<PictureData> {
                override fun onResponse(call: Call<PictureData>, response: Response<PictureData>) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value = AppState.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty())
                            liveDataToObserve.value = AppState.Error(Error("Unidentified error"))
                        else {
                            liveDataToObserve.value = AppState.Error(Error(message))
                        }

                    }
                }

                override fun onFailure(call: Call<PictureData>, t: Throwable) {
                    liveDataToObserve.value = AppState.Error(t)
                }

            })
        }

        return liveDataToObserve
    }

}