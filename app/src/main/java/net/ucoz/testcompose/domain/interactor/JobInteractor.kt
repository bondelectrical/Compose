package net.ucoz.testcompose.domain.interactor

import net.ucoz.testcompose.data.models.Job
import net.ucoz.testcompose.domain.common.NetworkResult
import net.ucoz.testcompose.domain.repository.JobRepository

interface JobInteractor {
    suspend fun getJobList(): NetworkResult<List<Job>>
}


class JobInteractorImpl(
    private val repository: JobRepository,
) :
    JobInteractor {

    override suspend fun getJobList(): NetworkResult<List<Job>> {
        return repository.getJobList()
    }
}