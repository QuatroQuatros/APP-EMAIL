package com.example.challengelocaweb.presentation.categories.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalFormCategory() {
    var title by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var groupByEmail by remember { mutableStateOf("") }
    var groupByWords by remember { mutableStateOf("") }
    // var selectedColor by remember { mutableStateOf(Color.Blue) }
    var notificationsEnabled by remember { mutableStateOf(false) }
    var privacyEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 10.dp),
                text = "Nova Categoria",
                fontSize = 24.sp,
                color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.textLight),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                TextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = it.isEmpty()
                    },
                    label = { Text("Escreva o título aqui") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.textLight)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        errorIndicatorColor = colorResource(id = R.color.dangerDark),
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.lightBlue),
                        unfocusedIndicatorColor = colorResource(id = R.color.lightBlue)
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                if (titleError) {
                    Text(
                        text = "Título é obrigatório",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = groupByEmail,
                    onValueChange = {
                        groupByEmail = it
                    },
                    label = { Text(
                        fontSize = 15.sp,
                        text = "Adicionar e-mail")
                            },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = colorResource(id = R.color.detailsFirst)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = colorResource(id = R.color.lightBlue),
                        focusedContainerColor = colorResource(id = R.color.lightBlue)
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = groupByWords,
                    onValueChange = {
                        groupByWords = it
                    },
                    label = { Text(
                        fontSize = 15.sp,
                        text = "Agrupar por palavras-chave")
                            },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.textLight)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = colorResource(id = R.color.lightBlue),
                        focusedContainerColor = colorResource(id = R.color.lightBlue)
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            BorderStroke(2.dp, colorResource(id = R.color.lightBlue)),
                            shape = RoundedCornerShape(17.dp)
                        )
                        .padding(start = 11.dp, end = 11.dp)
                ) {

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = colorResource(id = R.color.detailsFirst)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Me notifique",
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.weight(1f))

                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.lightBlue),
                        uncheckedThumbColor = colorResource(id = R.color.selected),
                        checkedTrackColor = colorResource(id = R.color.selected),
                        uncheckedTrackColor = colorResource(id = R.color.lightBlue),
                        checkedBorderColor = colorResource(id = R.color.primary),
                        uncheckedBorderColor = colorResource(id = R.color.selected)
                    )
                )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            BorderStroke(2.dp, colorResource(id = R.color.lightBlue)),
                            shape = RoundedCornerShape(17.dp)
                        )
                        .padding(start = 11.dp, end = 11.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = colorResource(id = R.color.detailsFirst)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Privar Categoria",
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Switch(
                        checked = privacyEnabled,
                        onCheckedChange = { privacyEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorResource(id = R.color.lightBlue),
                            uncheckedThumbColor = colorResource(id = R.color.selected),
                            checkedTrackColor = colorResource(id = R.color.selected),
                            uncheckedTrackColor = colorResource(id = R.color.lightBlue),
                            checkedBorderColor = colorResource(id = R.color.primary),
                            uncheckedBorderColor = colorResource(id = R.color.selected)
                        )
                    )
                }
            }
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ModalFormCategoryPreview() {
    ModalFormCategory();
}