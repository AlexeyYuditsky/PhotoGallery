package com.alexeyyuditsky.photogallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.photogallery.api.FlickrApi
import com.alexeyyuditsky.photogallery.api.FlickrResponse
import com.alexeyyuditsky.photogallery.api.PhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrFetchr"

// Класс "репозиторий", который хранит всю логику доступа на удаленный сервер
class FlickrFetchr {

    private val flickrApi: FlickrApi

    init {
        Log.d("TAG", "FlickrFetchr блок init в начале, создание экземпляра Retrofit")

        // Создаём экземпляр Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/") // Устанавливаем основной URL-адрес
            .addConverterFactory(GsonConverterFactory.create()) // Добавляем конвертер библиотеки Gson
            .build()

        // Retrofit создаёт экземпляр нашего интерфейса API (FlickrApi)
        flickrApi = retrofit.create(FlickrApi::class.java)

        Log.d("TAG", "FlickrFetchr блок init в конце, создание экземпляра Retrofit")
    }


    // Функция возвращает результат веб-запроса
    fun fetchPhotos(): LiveData<List<GalleryItem>> {
        Log.d("TAG", "FlickrFetchr.fetchPhotos() в начале метода выполняющий веб запрос")

        // Здесь храниться результат веб-запроса
        val responseLiveData: MutableLiveData<List<GalleryItem>> = MutableLiveData()

        // Получаем объект Call<FlickrResponse> выполняющий веб-запрос
        val flickrRequest: Call<FlickrResponse> = flickrApi.fetchPhotos()

        // Выполнение (в фоновом потоке) веб-запроса находящегося в объекте Call<FlickrResponse>
        flickrRequest.enqueue(object : Callback<FlickrResponse> {

            // Функция вызывается, если ответ от сервера ПОЛУЧЕН
            override fun onResponse(
                call: Call<FlickrResponse>, // Исходный объект вызова, используемый для инициирования запроса
                response: Response<FlickrResponse> // Содержимое результата веб-запроса
            ) {
                // Получаем десериализованное JSON тело ответа в объект модели
                val flickrResponse: FlickrResponse? = response.body()

                // Получаем из FlickrResponse поле объекта PhotoResponse
                val photoResponse: PhotoResponse? = flickrResponse?.photos

                // Получаем из PhotoResponse поле списка List<GalleryItem>, если его нет, то возращаем пустой список
                var galleryItems: List<GalleryItem> = photoResponse?.galleryItems ?: mutableListOf()

                // Пробегаемся по всем объектам GalleryItem и проверяем, если в GalleryItem поле url пустое, то пропускаем этот объект
                galleryItems = galleryItems.filterNot { it.url.isBlank() }

                // Кладём в MutableLiveData<List<GalleryItem>> объект List<GalleryItem> и возвращаем для дальнейшей передачи наблюдателю из фрагмента PhotoGalleryFragment
                responseLiveData.value = galleryItems
            }

            // Функция вызывается, если ответ от сервера НЕ ПОЛУЧЕН
            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.d(TAG, "Не удалось получить фотографии", t)
            }
        })
        Log.d("TAG", "FlickrFetchr.fetchPhotos() в конце метода выполняющий веб запрос")

        return responseLiveData
    }
}