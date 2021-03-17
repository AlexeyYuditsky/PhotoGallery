package com.alexeyyuditsky.photogallery.api

import com.alexeyyuditsky.photogallery.GalleryItem
import com.google.gson.annotations.SerializedName

// Класс отвечает за почти самый верхний объект иерархии JSON-данных - "photo":
class PhotoResponse {

    // Аннотация сообщает библиотеке Gson к какому JSON-полю относится свойство galleryItems: List<GalleryItem>
    @SerializedName("photo")
    // Свойство отвечает за хранение списка галерейных объектов
    lateinit var galleryItems: List<GalleryItem>
}