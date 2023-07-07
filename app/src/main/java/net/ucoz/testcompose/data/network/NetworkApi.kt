package net.ucoz.testcompose.data.network

import net.ucoz.testcompose.data.models.Job

interface NetworkApi {

    companion object {
        private const val BASE_URL = "https://744de05e-392b-4a89-96e6-9f76aa537dff.mock.pstmn.io"
        const val GET_URL = "${BASE_URL}/testGetJobList"
    }

    suspend fun getJobList(): List<Job>
}