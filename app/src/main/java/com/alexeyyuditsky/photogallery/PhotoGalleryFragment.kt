package com.alexeyyuditsky.photogallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {

    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "PhotoGalleryFragment.onCreate() в начале метода")

        // Создаём ViewModel
        photoGalleryViewModel = ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)

        Log.d("TAG", "PhotoGalleryFragment.onCreate() в конце метода")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "PhotoGalleryFragment.onCreateView() в начале метода")

        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)

        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)

        Log.d("TAG", "PhotoGalleryFragment.onCreateView() в конце метода")

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "PhotoGalleryFragment.onViewCreated() в начале метода")

        // Наблюдаем за LiveData(galleryItemLiveData) во ViewModel
        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner, // Аргумент гарантирует, что объект LiveData удалит наблюдателя при уничтожении представления фрагмента
            Observer { galleryItems ->
                Log.d(
                    "TAG",
                    "В наблюдателе за LiveData, передаем адаптеру результат веб-запроса = $galleryItems"
                )

                photoRecyclerView.adapter = PhotoAdapter(galleryItems)
            })

        Log.d("TAG", "PhotoGalleryFragment.onViewCreated() в конце метода")
    }


    // ViewHolder
    private class PhotoHolder(itemTextView: TextView) : RecyclerView.ViewHolder(itemTextView) {

        init {
            Log.d("TAG", "внутри PhotoHolder, создаём PhotoHolder(TextView)")
        }

        // Функция установки текста для TextView
        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }


    // Adapter
    private class PhotoAdapter(private val galleryItems: List<GalleryItem>) :
        RecyclerView.Adapter<PhotoHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            Log.d("TAG", "PhotoAdapter.onCreateViewHolder() создаём PhotoHolder(TextView) ")

            val textView = TextView(parent.context)
            return PhotoHolder(textView)
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val galleryItem = galleryItems[position]

            Log.d(
                "TAG",
                "PhotoAdapter.onBindViewHolder() galleryItem = ${galleryItem.title} и позиция = $position"
            )

            // Устанавливаем текст внутри ViewHolder для TextView
            holder.bindTitle(galleryItem.title)
        }

        override fun getItemCount() = galleryItems.size
    }


    // Создание и инициализация фрагмента PhotoGalleryFragment
    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}