package com.papayacoders.shoppinglistitemtesting.repository

import androidx.lifecycle.LiveData
import com.papayacoders.shoppinglistitemtesting.data.local.ShoppingDao
import com.papayacoders.shoppinglistitemtesting.data.local.ShoppingItem
import com.papayacoders.shoppinglistitemtesting.other.Resource
import com.papayacoders.shoppinglistitemtesting.remote.PixabayApi
import com.papayacoders.shoppinglistitemtesting.remote.response.ImageResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi
) : ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {

        shoppingDao.insertShoppingItem(shoppingItem)

    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
       return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {

        return try {
            val response = pixabayApi.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unkown error occured", null)
            }else{
             Resource.error("An unkown error occured", null)

            }
        }catch (e :Exception){
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }

    }

}