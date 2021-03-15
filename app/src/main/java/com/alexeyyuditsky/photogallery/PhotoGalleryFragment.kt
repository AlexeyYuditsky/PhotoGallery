package com.alexeyyuditsky.photogallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.photogallery.api.FlickrApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {

    private lateinit var photoRecyclerView: RecyclerView


    // Создание и инициализация Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаём экземпляр Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/") // Устанавливаем базовы Url
            .addConverterFactory(ScalarsConverterFactory.create()) // Добавляем скалярный конвертер
            .build()

        // Retrofit создаёт экземпляр нашего интерфейса API (FlickrApi)
        val flickrApi: FlickrApi = retrofit.create(FlickrApi::class.java)

        // Получаем объект Call<String> выполняющий веб-запрос
        val flickrHomePageRequest: Call<String> = flickrApi.fetchContents()

        // Выполнение (в фоновом потоке) веб-запроса находящегося в объекте Call
        flickrHomePageRequest.enqueue(object : Callback<String> {

            // Функция вызывается, если ответ от сервера ПОЛУЧЕН
            override fun onResponse(
                call: Call<String>, // Исходный объект вызова, используемый для инициирования запроса
                response: Response<String> // Содержимое результата
            ) {
                Log.d(TAG, "Ответ получен: \n${response.body()}")
            }

            // Функция вызывается, если ответ от сервера НЕ ПОЛУЧЕН
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "Не удалось получить фотографии", t)
            }
        })
    }


    // Создание и инициализация RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)

        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)

        return view
    }


    // Создание и инициализация фрагмента PhotoGalleryFragment
    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}