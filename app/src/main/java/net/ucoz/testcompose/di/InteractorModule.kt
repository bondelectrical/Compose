package net.ucoz.testcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.ucoz.testcompose.domain.interactor.JobInteractor
import net.ucoz.testcompose.domain.interactor.JobInteractorImpl
import net.ucoz.testcompose.domain.repository.JobRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Provides
    @Singleton
    fun providesJobInteractor(
        repository: JobRepository
    ): JobInteractor = JobInteractorImpl(repository)
}