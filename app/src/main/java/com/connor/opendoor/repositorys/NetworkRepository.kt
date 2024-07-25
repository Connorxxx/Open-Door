package com.connor.opendoor.repositorys

import com.connor.opendoor.datasources.network.DoorSource
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
            ResponseState.Error(it)
        },
        ifRight = {
            ResponseState.Success(it)
        }
    )
}