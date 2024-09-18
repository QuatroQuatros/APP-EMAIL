package com.example.challengelocaweb.presentation.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R

@Composable
fun RegisterButton(value: String, onClick: () -> Unit) {
    Button(onClick = { onClick() },
        modifier = Modifier
            .width(150.dp)
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(modifier = Modifier
            .width(150.dp)
            .heightIn(48.dp)
            .background(
                color = if (isSystemInDarkTheme()) colorResource(id = R.color.secondaryButtonsDark) else colorResource(
                    id = R.color.navDark
                ),
                shape = RoundedCornerShape(30.dp)
            ),
            contentAlignment = Alignment.Center
        )
        {
            Text(text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}