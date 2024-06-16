package com.example.challengelocaweb.presentation.event.components

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R
import com.example.challengelocaweb.data.repository.mocks.mockEventRepository
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.presentation.event.EventViewModel
import com.example.challengelocaweb.util.isValidTime
import java.time.LocalDate

import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalForm(
    selectedDate: LocalDate,
    onDismiss: () -> Unit,
    viewModel: EventViewModel
) {
    val currentLocation by viewModel.currentLocation.observeAsState("")
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var selectedEventType by remember { mutableStateOf(EventTypeEnum.EVENT) }
    var selectedDay by remember { mutableStateOf(selectedDate.dayOfMonth) }
    var isAllDay by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf("08:00") }
    var endTime by remember { mutableStateOf("09:00") }
    var isUnique by remember { mutableStateOf(true) }
    var isNotifiable by remember { mutableStateOf(true) }
    var showParticipantsInput by remember { mutableStateOf(false) }
    var showLocationInput by remember { mutableStateOf(false) }
    var showNotificationInput by remember { mutableStateOf(false) }
    var showDescriptionInput by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf(currentLocation) }

    var titleError by remember { mutableStateOf(false) }
    var startTimeError by remember { mutableStateOf(false) }
    var endTimeError by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 25.dp, horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Novo Evento",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primary),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            EventTypeButton(
                text = "Evento",
                isSelected = selectedEventType == EventTypeEnum.EVENT,
                onClick = { selectedEventType = EventTypeEnum.EVENT }
            )
            EventTypeButton(
                text = "Reunião",
                isSelected = selectedEventType == EventTypeEnum.MEETING,
                onClick = { selectedEventType = EventTypeEnum.MEETING }
            )
            EventTypeButton(
                text = "Lembrete",
                isSelected = selectedEventType == EventTypeEnum.REMINDER,
                onClick = { selectedEventType = EventTypeEnum.REMINDER }
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
                OutlinedTextField(
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
                            tint = colorResource(id = R.color.primary)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorBorderColor = Color.Red,

                    )

                )
                if (titleError) {
                    Text(
                        text = "Título é obrigatório",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                    OutlinedTextField(
                        value = link,
                        onValueChange = { link = it },
                        label = { Text("Link (opcional)") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                tint = colorResource(id = R.color.primary)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isAllDay,
                        onCheckedChange = { isAllDay = it }
                    )
                    Text("Dia inteiro")
                }

                if (!isAllDay) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        OutlinedTextField(
                            value = startTime,
                            onValueChange = {
                                startTime = it
                                startTimeError = it.isEmpty() || !isValidTime(it)
                             },
                            label = { Text("Hora Início") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.primary)
                                )
                            },
                            isError = startTimeError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.weight(1f),
                            colors =  TextFieldDefaults.outlinedTextFieldColors(
                                errorBorderColor = Color.Red,
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = endTime,
                            onValueChange = {
                                endTime = it
                                endTimeError = it.isEmpty() || !isValidTime(it)
                            },
                            label = { Text("Hora Fim") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.primary)
                                )
                            },
                            isError = endTimeError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                errorBorderColor = Color.Red,
                            ),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isUnique,
                        onCheckedChange = { isUnique = it }
                    )
                    Text("Não se repete")
                }

                ClickableRow(
                    text = "Adicionar participantes",
                    icon = Icons.Default.Person,
                    onClick = { showParticipantsInput = !showParticipantsInput }
                )
                if (showParticipantsInput) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Participantes") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = colorResource(id = R.color.primary)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                ClickableRow(
                    text = "Adicionar local",
                    icon = Icons.Default.LocationOn,
                    onClick = {
                        showLocationInput = !showLocationInput
                    }
                )
                if (showLocationInput) {
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it},
                        label = { Text("Local") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = colorResource(id = R.color.primary)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                ClickableRow(
                    text = "Adicionar notificação",
                    icon = Icons.Default.Notifications,
                    onClick = { showNotificationInput = !showNotificationInput }
                )
                if (showNotificationInput) {
                    Switch(
                        modifier = Modifier.height(20.dp),
                        checked = isNotifiable,
                        onCheckedChange = { isNotifiable = true },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = colorResource(id = R.color.primary),
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.Gray)
                    )
//                    OutlinedTextField(
//                        value = isNotifiable,
//                        onValueChange = {isNotifiable = it},
//                        label = { Text("Notificação") },
//                        leadingIcon = {
//                            Icon(
//                                imageVector = Icons.Default.Notifications,
//                                contentDescription = null,
//                                tint = colorResource(id = R.color.primary)
//                            )
//                        },
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType = KeyboardType.Text,
//                            imeAction = ImeAction.Done
//                        ),
//                        modifier = Modifier.fillMaxWidth()
//                    )
                }
                ClickableRow(
                    text = "Adicionar descrição",
                    icon = Icons.Default.Menu,
                    onClick = { showDescriptionInput = !showDescriptionInput }
                )
                if (showDescriptionInput) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descrição") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = colorResource(id = R.color.primary)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        titleError = title.isEmpty()
                        startTimeError = startTime.isEmpty() || !isValidTime(startTime)
                        endTimeError = endTime.isEmpty() || !isValidTime(endTime)
                        locationError = location.isEmpty()

                        if (!titleError && !startTimeError && !endTimeError && !locationError) {
                            val event = Event(
                                title = title,
                                description = description,
                                link = link,
                                eventType = selectedEventType,
                                day = selectedDay.toString(),
                                location = location,
                                startTime = LocalTime.parse(startTime),
                                endTime = LocalTime.parse(endTime),
                                isUnique = isUnique,
                                isAllDay = isAllDay,
                                isNotifiable = isNotifiable,
                                createdAt = selectedDate.toString()
                            )
                            viewModel.saveEvent(event)
                            onDismiss()
                        }

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Criar Evento")
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CreateModalTestPreview() {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val mockViewModel = mockEventRepository(application)

    ModalForm(LocalDate.now(), onDismiss = {}, viewModel = mockViewModel)

}
