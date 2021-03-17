package com.alexeyyuditsky.photogallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {

    private lateinit var photoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Возвращаем результат веб-запроса
        val flickrLiveData: LiveData<List<GalleryItem>> = FlickrFetchr().fetchPhotos()

        // Наблюдаем за полученными данными веб-запроса
        flickrLiveData.observe(
            this,
            Observer { galleryItems -> // Observer - простой обратный вызов который можно получить от LiveData
                Log.d(TAG, "Ответ получен: $galleryItems")
            }
        )
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