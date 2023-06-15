package com.papayacoders.shoppinglistitemtesting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papayacoders.shoppinglistitemtesting.data.local.ShoppingItem
import com.papayacoders.shoppinglistitemtesting.other.Constant
import com.papayacoders.shoppinglistitemtesting.other.Event
import com.papayacoders.shoppinglistitemtesting.other.Resource
import com.papayacoders.shoppinglistitemtesting.other.Status
import com.papayacoders.shoppinglistitemtesting.remote.response.ImageResponse
import com.papayacoders.shoppinglistitemtesting.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {



    val shoppingItems = repository.observeAllShoppingItem()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images : LiveData<Event<Resource<ImageResponse>>> = _images


    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl : LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus : MutableLiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus


    fun setImageUrl(url : String){
        _curImageUrl.postValue(url)

    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }


    private fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString : String, priceString : String)  {

        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The field must not be empty", null)))
            return
        }
        if(name.length > Constant.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the item exceeded", null)))
            return
        }
        if(priceString.length > Constant.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price of the item exceeded", null)))
            return
        }

        val amount = try {
            amountString.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter valid amount", null)))
            return
        }

        val shoppingItem = ShoppingItem(
            name, amount, priceString.toFloat(),
            _curImageUrl.value?:""
        )
        insertShoppingItemIntoDb(shoppingItem)
        setImageUrl("")

        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))

    }

    fun searchForImage(imageQuery: String)  {

        if (imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }

    }



}