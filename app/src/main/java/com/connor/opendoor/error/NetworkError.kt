package com.connor.opendoor.error

import io.ktor.http.HttpStatusCode

sealed interface NetworkError<out R> {
    data class Request(val message: String) : NetworkError<Nothing>
    data class HttpStatus(val code: HttpStatusCode) : NetworkError<Nothing>
    data class Response<out T>(val content: T) : NetworkError<T>
}