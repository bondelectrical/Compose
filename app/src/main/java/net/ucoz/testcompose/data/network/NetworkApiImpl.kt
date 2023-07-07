package net.ucoz.testcompose.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import net.ucoz.testcompose.data.models.Job


class NetworkApiImpl(private val client: HttpClient) : NetworkApi {

    override suspend fun getJobList(): List<Job> {

        return client.get { url("https://de1c4168-39af-4904-828b-71eeedf93e89.mock.pstmn.io/getJobList")  }
    }

}