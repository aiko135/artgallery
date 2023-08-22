package ktepin.penzasoft.artgallery.domain.model

sealed class ApiRequestResult<T>()

class RequestSuccess<T>(
    val entity: T
) : ApiRequestResult<T>()

class RequestError<T>(
    val message: String,
) : ApiRequestResult<T>()