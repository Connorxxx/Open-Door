package com.connor.opendoor.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.connor.opendoor.intents.HomeEvent
import com.connor.opendoor.intents.ResponseState
import com.connor.opendoor.ui.theme.OpenDoorTheme
import com.connor.opendoor.utlis.logCat
import com.connor.opendoor.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
) {
    val state by vm.state.collectAsState()
    val observer = remember {
        object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                vm.onEvent(HomeEvent.Open)
            }
        }
    }
    DisposableEffect(observer) {
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
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
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = { onClick(HomeEvent.Open) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.width(140.dp),
            enabled = responseState != ResponseState.Loading
        ) {
            if (responseState == ResponseState.Loading) CircularProgressIndicator(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .width(20.dp)
                    .height(20.dp),
                color = Color.White,
                strokeWidth = 2.dp,
            )
            else Text(
                text = when (responseState) {
                    is ResponseState.Error -> "Error 💩"
                    is ResponseState.Idle -> "Open"
                    is ResponseState.Loading -> ""
                    is ResponseState.Success -> "Success 😁"
                },
                color = Color.White,
                fontSize = 18.sp,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OpenDoorTheme {
        Home()
    }
}