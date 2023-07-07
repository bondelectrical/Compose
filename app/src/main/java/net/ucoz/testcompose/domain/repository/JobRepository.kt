package net.ucoz.testcompose.domain.repository

import net.ucoz.testcompose.data.models.Job
import net.ucoz.testcompose.domain.common.NetworkResult

interface JobRepository {
    suspend fun getJobList(): NetworkResult<List<Job>>
}