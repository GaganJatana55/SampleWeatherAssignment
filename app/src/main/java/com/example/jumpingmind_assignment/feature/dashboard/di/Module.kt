package com.example.jumpingmind_assignment.feature.dashboard.di

import com.example.jumpingmind_assignment.feature.common.roomStorage.UsersDao
import com.example.jumpingmind_assignment.feature.dashboard.api.APIService
import com.example.jumpingmind_assignment.feature.dashboard.repo.RepoInterface
import com.example.jumpingmind_assignment.feature.dashboard.repo.Repository
import com.example.jumpingmind_assignment.utils.InternetConnectivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideRepo(
        apiService: APIService,
        usersDao: UsersDao,
        internetConnectivity: InternetConnectivity
    ): RepoInterface {
        return Repository(apiService, usersDao, internetConnectivity)
    }


}