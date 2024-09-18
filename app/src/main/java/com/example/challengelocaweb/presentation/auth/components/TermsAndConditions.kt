package com.example.challengelocaweb.presentation.auth.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R

@Composable
fun TermsAndConditions(value: String){
    Row(modifier = Modifier
        .width(300.dp)
        .height(65.dp)
        .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {

        var checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = !checkedState.value },
        )

        Text(text = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            style = TextStyle(
                fontSize = 12.sp,
                fontStyle = FontStyle.Normal),
            textAlign = TextAlign.Center,
            color = if (isSystemInDarkTheme()) colorResource(id = R.color.formBlue) else colorResource(id = R.color.textLight),
        )
    }
}