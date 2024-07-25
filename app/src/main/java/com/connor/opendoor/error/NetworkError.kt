package com.connor.opendoor.error

import io.ktor.http.HttpStatusCode

sealed interface NetworkError {
    data class Request(val message: String) : NetworkError
    data class HttpStatus(val code: HttpStatusCode) : NetworkError
    data class Response(val content: String) : NetworkError
}