package com.connor.opendoor.ui.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connor.opendoor.intents.HomeEvent
import com.connor.opendoor.ui.theme.Typography

@Composable
fun LoadingButton(
    onClick: (event: HomeEvent) -> Unit,
    loading: Boolean,
    text: String,
) {
    Button(
        onClick = { onClick(HomeEvent.Open) },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.width(140.dp),
        enabled = !loading
    ) {
        if (loading) CircularProgressIndicator(
            modifier = Modifier
                .padding(end = 12.dp)
                .width(20.dp)
                .height(20.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 2.dp,
        )
        else Text(
            text = text,
            style = Typography.titleMedium,
            maxLines = 1,
        )
    }
}