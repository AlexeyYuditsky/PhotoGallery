package com.alexeyyuditsky.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PhotoGalleryViewModel : ViewModel() {

    // Свойство для хранения объекта класса FlickrFetchr
    private val flickrFetchr: FlickrFetchr = FlickrFetchr()

    // Свойство для хранения LiveData содержащий список элементов галереи
    val galleryItemLiveData: LiveData<List<GalleryItem>> =
        // Выполняем веб-запрос для получения данных фото при первой инициализации ViewModel и сохраняем результат веб-запроса
        flickrFetchr.fetchPhotos()


    init {
        Log.d(
            "TAG",
            "PhotoGalleryViewModel инициализация класса и получение результата веб-запроса, результат: $galleryItemLiveData"
        )
    }


    // Функция вызывается, перед уничтожение ViewModel
    override fun onCleared() {
        super.onCleared()

        // Отменяем веб-запрос, перед уничтожением ViewModel
        flickrFetchr.cancelRequestInFlight()
    }
}