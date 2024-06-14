package com.example.challengelocaweb.presentation.calendar.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.challengelocaweb.R

@Composable
fun FloatingActionButton(
    onClick: () -> Unit
) {
    androidx.compose.material3.FloatingActionButton(
        modifier = Modifier
            .padding(40.dp, bottom = 80.dp),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "Add",
            tint = colorResource(id = R.color.primary)
        )
    }
}