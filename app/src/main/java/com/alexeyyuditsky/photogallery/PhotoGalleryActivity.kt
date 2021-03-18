package com.alexeyyuditsky.photogallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class PhotoGalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        // Если фрагмент(PhotoGalleryFragment) ещё не создан, то получаем FragmentManager, создаём фрагмент(PhotoGalleryFragment) и размещаем его в макете activity_photo_gallery.xml в контейнере R.id.fragmentContainer
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, PhotoGalleryFragment.newInstance())
                .commit()
        }
    }
}