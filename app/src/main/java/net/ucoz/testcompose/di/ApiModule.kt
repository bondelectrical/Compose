package net.ucoz.testcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import net.ucoz.testcompose.data.network.NetworkApi
import net.ucoz.testcompose.data.network.NetworkApiImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideNetworkApi(client: HttpClient): NetworkApi = NetworkApiImpl(client)
    }