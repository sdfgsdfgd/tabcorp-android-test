package kaan.tabcorp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kaan.tabcorp.App
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UIModule {
    @Singleton
    @Provides
    fun provideApplicationContext() = App()
}
