package com.tmonet.hcetest.data.di

import com.tmonet.hcetest.data.repository.UserRepository
import com.tmonet.hcetest.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {
    @Binds
    abstract fun bindsUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository
}