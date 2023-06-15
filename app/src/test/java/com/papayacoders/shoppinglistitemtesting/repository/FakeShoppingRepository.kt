package com.papayacoders.shoppinglistitemtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papayacoders.shoppinglistitemtesting.data.local.ShoppingItem
import com.papayacoders.shoppinglistitemtesting.other.Resource
import com.papayacoders.shoppinglistitemtesting.remote.response.ImageResponse

class FakeShoppingRepository : ShoppingRepository  {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observeShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value : Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observeShoppingItem.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {

        return shoppingItems.sumByDouble {
            it.price.toDouble()
        }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)

        refreshLiveData()

    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observeShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("Error", null)
        }else{
            Resource.success(ImageResponse(listOf(), 0, 0 ))
        }
    }


}