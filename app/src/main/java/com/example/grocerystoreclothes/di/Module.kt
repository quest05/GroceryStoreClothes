package com.example.grocerystoreclothes.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE my_database ADD COLUMN isReturn INTEGER NOT NULL DEFAULT 0")
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MyDefaultProductDb =
        Room.databaseBuilder(app, MyDefaultProductDb::class.java, MY_PRODUCT_DATABASE)
            .build()
    /*Room.databaseBuilder(app, MyDefaultProductDb::class.java, "my_database")
    .addMigrations(MIGRATION_1_2)
    .build()*/

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
