package com.papayacoders.shoppinglistitemtesting.di

import android.content.Context
import androidx.room.Room
import com.papayacoders.shoppinglistitemtesting.data.local.ShoppingDao
import com.papayacoders.shoppinglistitemtesting.data.local.ShoppingItemDatabase
import com.papayacoders.shoppinglistitemtesting.other.Constant.BASE_URL
import com.papayacoders.shoppinglistitemtesting.other.Constant.DATABASE_NAME
import com.papayacoders.shoppinglistitemtesting.remote.PixabayApi
import com.papayacoders.shoppinglistitemtesting.repository.DefaultShoppingRepository
import com.papayacoders.shoppinglistitemtesting.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayApi

    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository


    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()


    @Singleton
    @Provides
    fun providesShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()


    @Singleton
    @Provides
    fun providesPixabayApi(): PixabayApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(PixabayApi::class.java)
    }


}