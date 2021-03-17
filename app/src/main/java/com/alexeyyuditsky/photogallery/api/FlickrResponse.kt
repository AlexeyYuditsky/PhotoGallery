package com.alexeyyuditsky.photogallery.api

// Класс отвечает за самый верхний объект иерархии JSON-данных - "photos":
class FlickrResponse {
    lateinit var photos: PhotoResponse
}