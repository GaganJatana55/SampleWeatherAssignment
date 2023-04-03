package com.example.jumpingmind_assignment.feature.common.roomStorage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserStorageRoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, UserDataBaseConstants.databaseName)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(appDatabase: UserDatabase) =
        appDatabase.userDAo()
}