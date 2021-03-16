package com.alexeyyuditsky.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.photogallery.api.FlickrApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "FlickrFetchr"

// Класс "репозиторий", который хранит всю логику доступа на удаленный сервер
class FlickrFetchr {

    private val flickrApi: FlickrApi

    init {
        // Создаём экземпляр Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/") // Устанавливаем базовы Url
            .addConverterFactory(ScalarsConverterFactory.create()) // Добавляем скалярный конвертер
            .build()

        // Retrofit создаёт экземпляр нашего интерфейса API (FlickrApi)
        flickrApi = retrofit.create(FlickrApi::class.java)
    }


    // Функция отвечает за веб-запрос
    fun fetchContents(): LiveData<String> {

        val responseLiveData: MutableLiveData<String> = MutableLiveData()

        // Получаем объект Call<String> выполняющий веб-запрос
        val flickrRequest: Call<String> = flickrApi.fetchContents()

        // Выполнение (в фоновом потоке) веб-запроса находящегося в объекте Call<String>(flickrRequest)
        flickrRequest.enqueue(object : Callback<String> {

            // Функция вызывается, если ответ от сервера ПОЛУЧЕН
            override fun onResponse(
                call: Call<String>, // Исходный объект вызова, используемый для инициирования запроса
                response: Response<String> // Содержимое результата
            ) {
                responseLiveData.value = response.body() // Возвращает десериализованное тело ответа
            }

            // Функция вызывается, если ответ от сервера НЕ ПОЛУЧЕН
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "Не удалось получить фотографии", t)
            }
        })

        // Результат веб-запроса возвращается до завершения запроса flickrRequest
        return responseLiveData
    }
}