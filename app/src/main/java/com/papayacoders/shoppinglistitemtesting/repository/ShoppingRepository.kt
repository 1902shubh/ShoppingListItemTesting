package com.papayacoders.shoppinglistitemtesting.repository

import androidx.lifecycle.LiveData
import com.papayacoders.shoppinglistitemtesting.data.local.ShoppingItem
import com.papayacoders.shoppinglistitemtesting.other.Resource
import com.papayacoders.shoppinglistitemtesting.remote.response.ImageResponse
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItem() : LiveData<List<ShoppingItem>>
    fun observeTotalPrice() : LiveData<Float>
    suspend fun searchForImage(imageQuery : String) : Resource<ImageResponse>


}