package com.example.challengelocaweb.presentation.event.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
            .padding(40.dp, bottom = 80.dp)
            .size(50.dp),

        onClick = onClick,
        shape = RoundedCornerShape(40.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "Add",
            tint = colorResource(id = R.color.white)
        )
    }
}