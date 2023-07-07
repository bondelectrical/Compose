package net.ucoz.testcompose.data.repository

import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.ucoz.testcompose.data.models.Job
import net.ucoz.testcompose.data.network.NetworkApi
import net.ucoz.testcompose.domain.common.NetworkResult
import net.ucoz.testcompose.domain.repository.JobRepository

class JobRepositoryImpl(
    private val networkApi: NetworkApi
): JobRepository {
    override suspend fun getJobList(): NetworkResult<List<Job>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = networkApi.getJobList()
                NetworkResult.SuccessWithData(result)
            }catch (exception: RedirectResponseException) {
                // 3xx - responses
//                println("Error: ${exception.response.status.description}")
                exception.printStackTrace()
                NetworkResult.Error(exception)
            } catch (exception: ClientRequestException) {
                // 4xx - responses
//                println("Error: ${exception.response.status.description}")
                exception.printStackTrace()
                NetworkResult.Error(exception)
            } catch (exception: ServerResponseException) {
                // 5xx - response
//                println("Error: ${exception.response.status.description}")
                NetworkResult.ServerError(exception.response.status.description)
            }catch (exception: Exception) {
                exception.printStackTrace()
                NetworkResult.Error(exception)
            }
        }
    }
}