package com.example.mybasicapplication.di

import com.example.data.repository.UserRepositoryImp
import com.example.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(repo : UserRepositoryImp) : UserRepository

}