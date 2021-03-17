package com.alexeyyuditsky.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PhotoGalleryViewModel : ViewModel() {

    // Свойство для хранения LiveData содержащий список элементов галереи
    val galleryItemLiveData: LiveData<List<GalleryItem>> =
        // Выполняем веб-запрос для получения данных фото при первой инициализации ViewModel и сохраняем полученные данные в созданное нами свойство
        FlickrFetchr().fetchPhotos()
}