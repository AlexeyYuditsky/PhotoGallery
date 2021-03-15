package com.alexeyyuditsky.photogallery.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    // Аннотация настраивает Call, возвращаемый функцией fetchContents(), на выполнение GET-запроса. Относительный путь «/» означает, что запрос будет отправлен на базовый URL-адрес
    @GET("/")
    fun fetchContents(): Call<String>

}