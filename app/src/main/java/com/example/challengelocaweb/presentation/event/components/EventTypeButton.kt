package com.example.challengelocaweb.presentation.event.components


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R

@Composable
fun EventTypeButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(

        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorResource(id = R.color.selected) else Color.LightGray,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(40.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}