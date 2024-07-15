package com.ibrahim.db.di

import android.content.Context
import androidx.room.Room
import com.ibrahim.db.AppDB
import com.ibrahim.db.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): AppDB {
        return Room.databaseBuilder(context,AppDB::class.java,"DB").build()
    }

    @Provides
    fun providePostsDao(database: AppDB): PostDao {
        return database.postDao()
    }
}