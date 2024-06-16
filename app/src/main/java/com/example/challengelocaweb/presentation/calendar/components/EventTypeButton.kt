package com.example.challengelocaweb.presentation.calendar.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.challengelocaweb.R

@Composable
fun EventTypeButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(id = R.color.selected) else Color.LightGray,
            contentColor = if (isSelected) Color.White else Color.Black
        ),

    ) {
        Text(text)
    }
}