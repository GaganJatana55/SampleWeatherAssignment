package com.example.jumpingmind_assignment.feature.registration.di
import com.example.jumpingmind_assignment.feature.common.roomStorage.UsersDao
import com.example.jumpingmind_assignment.feature.registration.repo.RepoRegistrationInterface
import com.example.jumpingmind_assignment.feature.registration.repo.RepositoryRegistration
import com.example.jumpingmind_assignment.utils.InternetConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Singleton
    @Provides
    fun provideRepo(dataStoreI: UsersDao,internetConnectivity: InternetConnectivity): RepoRegistrationInterface {
        return RepositoryRegistration(dataStoreI,internetConnectivity)
    }





}