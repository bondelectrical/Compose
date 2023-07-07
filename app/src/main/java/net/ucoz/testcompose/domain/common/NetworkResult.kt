package net.ucoz.testcompose.domain.common


sealed class NetworkResult<T> {

    class SuccessWithData<T>(val data: T) : NetworkResult<T>()

    class ServerError<T>(val message: String) : NetworkResult<T>()

    class Error<T>(val exception: Exception) : NetworkResult<T>()
}