package ru.lachesis.nasapictures.app

import ru.lachesis.nasapictures.model.PictureData

sealed class AppState{
    data class Success(val serverResponseData: PictureData) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
