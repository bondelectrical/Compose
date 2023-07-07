package net.ucoz.testcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.ucoz.testcompose.data.network.NetworkApi
import net.ucoz.testcompose.data.repository.JobRepositoryImpl
import net.ucoz.testcompose.domain.repository.JobRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesJobRepository(
        api: NetworkApi
    ): JobRepository {
        return JobRepositoryImpl(api)
    }

}