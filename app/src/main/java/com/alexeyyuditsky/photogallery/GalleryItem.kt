package com.alexeyyuditsky.photogallery

import com.google.gson.annotations.SerializedName

// Класс отвечает за самый нижний объект иерархии JSON-данных - "photo":
class GalleryItem(
    var title: String = "",
    var id: String = "",

    // Аннотация сообщает библиотеке Gson к какому JSON-полю относится свойство url: String
    @SerializedName("url_s")
    var url: String = ""
)