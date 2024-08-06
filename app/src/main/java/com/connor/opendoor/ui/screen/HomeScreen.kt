package com.connor.opendoor.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.connor.opendoor.intents.HomeEvent
import com.connor.opendoor.intents.ResponseState
import com.connor.opendoor.ui.common.LoadingButton
import com.connor.opendoor.ui.theme.OpenDoorTheme
import com.connor.opendoor.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    owner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val state by vm.state.collectAsState()
    val observer = remember {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) vm.onEvent(HomeEvent.Open)
        }
    }
    DisposableEffect(owner) {
        owner.lifecycle.addObserver(observer)
        onDispose {
            owner.lifecycle.removeObserver(observer)
        }
    }
    Home(
        responseState = state.responseState,
        onClick = vm::onEvent
    )
}

@Composable
private fun Home(
    responseState: ResponseState<String> = ResponseState.Idle,
    onClick: (event: HomeEvent) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LoadingButton(
            onClick = onClick,
            loading = responseState is ResponseState.Loading,
            text = when (responseState) {
                is ResponseState.Error -> "Error 💩"
                is ResponseState.Idle -> "Open"
                is ResponseState.Success -> "Success 😁"
                ResponseState.Loading -> ""
            }
        )
        Text(
            text = if (responseState is ResponseState.Error) responseState.tips else "",
            modifier = Modifier.padding(top = 12.dp),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OpenDoorTheme {
        Home()
    }
}