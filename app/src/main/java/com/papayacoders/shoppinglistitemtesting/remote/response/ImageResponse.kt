package com.papayacoders.shoppinglistitemtesting.remote.response

data class ImageResponse (
    val hits : List<ImageResult>,
    val total : Int,
    val totalHits : Int
        )