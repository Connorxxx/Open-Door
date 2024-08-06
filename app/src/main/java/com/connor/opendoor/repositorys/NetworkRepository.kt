package com.connor.opendoor.repositorys

import com.connor.opendoor.datasources.network.DoorSource
import com.connor.opendoor.error.NetworkError
import com.connor.opendoor.intents.ResponseState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class NetworkRepository @Inject constructor(
    private val doorSource: DoorSource
) {

    fun getResponseState() = flow {
        emit(open())
    }.onStart {
        emit(ResponseState.Loading)
    }.onCompletion {
        delay(3.seconds)
        emit(ResponseState.Idle)
    }

    private suspend fun open() = doorSource.open().fold(
        ifLeft = {
            when (it) {
                is NetworkError.Request -> ResponseState.Error(it.message, "Request failed, check network connection")
                is NetworkError.HttpStatus -> ResponseState.Error(it.code.toString(), "Http status error code: ${it.code}")
                is NetworkError.Response -> ResponseState.Error(it.content, "Response error")
            }
        },
        ifRight = {
            ResponseState.Success(it)
        }
    )
}