package com.alexeyyuditsky.photogallery.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    // Аннотация определяет запрос "получить недавние интересные фотографии". Аннотация настраивает Call<String>, возвращаемый функцией fetchContents(), на выполнение GET-запроса.
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=fbc38de5cae80aed3c212de680b96594" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    fun fetchPhotos(): Call<FlickrResponse>
}