package com.connor.opendoor.intents

sealed interface HomeEvent {
    data object Open : HomeEvent
}

data class HomeState(
    val responseState: ResponseState<String> = ResponseState.Idle,
)

sealed interface ResponseState<out R> {
    data object Idle : ResponseState<Nothing>
    data object Loading: ResponseState<Nothing>
    data class Success<out T>(val data: T) : ResponseState<T>
    data class Error(val e: String, val tips: String) : ResponseState<Nothing>
}