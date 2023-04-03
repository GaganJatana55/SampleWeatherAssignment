package com.example.jumpingmind_assignment.feature.common.di

import android.app.Application
import android.content.Context

import com.example.jumpingmind_assignment.feature.common.dataStore.DataStoreImpl
import com.example.jumpingmind_assignment.feature.dashboard.api.APIService
import com.example.jumpingmind_assignment.feature.common.dataStore.DataStoreI
import com.example.jumpingmind_assignment.utils.InternetConnectivity
import com.example.jumpingmind_assignment.utils.InternetConnectivityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonProviderModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"


    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()




    @Provides
    fun provideRetrofitService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }


    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideInternetConnectivity(context: Context): InternetConnectivity {
        return InternetConnectivityImpl(context)
    }

    @Singleton
    @Provides
    fun provideDataStore(context: Context): DataStoreI {
        return DataStoreImpl(context)
    }

}