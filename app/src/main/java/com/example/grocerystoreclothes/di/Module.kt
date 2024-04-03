package com.example.grocerystoreclothes.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.example.grocerystoreclothes.Constants.MY_PRODUCT_DATABASE
import com.example.grocerystoreclothes.preferences.MyPreference
import com.example.grocerystoreclothes.roomdb.MyDefaultProductDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): MyDefaultProductDb =
        Room.databaseBuilder(app, MyDefaultProductDb::class.java, MY_PRODUCT_DATABASE)
            .build()

  /*  @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
    }*/

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Singleton
    @Provides
    fun provideMyPreference(sharedPreferences: Application): MyPreference {
        return MyPreference(sharedPreferences)
    }

}
