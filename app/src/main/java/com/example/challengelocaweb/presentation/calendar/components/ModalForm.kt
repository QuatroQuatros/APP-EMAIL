package com.example.challengelocaweb.presentation.calendar.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R
import java.time.LocalDate

@Composable
fun ModalForm(
    selectedDate: LocalDate,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedEventType by remember { mutableStateOf(EventType.EVENT) }
    var isAllDay by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf("08:00") }
    var endTime by remember { mutableStateOf("09:00") }
    var meetingLink by remember { mutableStateOf("") }

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
            modifier = Modifier.fillMaxWidth(),
//            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            EventTypeButton(
                text = "Evento",
                isSelected = selectedEventType == EventType.EVENT,
                onClick = { selectedEventType = EventType.EVENT }
            )
            EventTypeButton(
                text = "Reunião",
                isSelected = selectedEventType == EventType.MEETING,
                onClick = { selectedEventType = EventType.MEETING }
            )
            EventTypeButton(
                text = "Lembrete",
                isSelected = selectedEventType == EventType.REMINDER,
                onClick = { selectedEventType = EventType.REMINDER }
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
                    onValueChange = { title = it },
                    label = { Text("Escreva o título aqui") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = colorResource(id = R.color.primary)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (selectedEventType == EventType.MEETING) {
                    OutlinedTextField(
                        value = meetingLink,
                        onValueChange = { meetingLink = it },
                        label = { Text("Link da Reunião") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = null,
                                tint = colorResource(id = R.color.primary)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

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
                            onValueChange = { startTime = it },
                            label = { Text("Hora Início") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.primary)
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = endTime,
                            onValueChange = { endTime = it },
                            label = { Text("Hora Fim") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.primary)
                                )
                            },
                            modifier = Modifier.weight(1f)
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
                        checked = isAllDay,
                        onCheckedChange = { isAllDay = it }
                    )
                    Text("Não se repete")
                }

                ClickableRow(
                    text = "Adicionar participantes",
                    icon = Icons.Default.Person,
                    onClick = { /* Adicionar participantes */ }
                )
                ClickableRow(
                    text = "Adicionar local",
                    icon = Icons.Default.LocationOn,
                    onClick = { /* Adicionar local */ }
                )
                ClickableRow(
                    text = "Adicionar notificação",
                    icon = Icons.Default.Notifications,
                    onClick = { /* Adicionar notificação */ }
                )
                ClickableRow(
                    text = "Adicionar descrição",
                    icon = Icons.Default.Menu,
                    onClick = { /* Adicionar descrição */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* TODO: Implement event creation logic */ },
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

    ModalForm(LocalDate.now(), onDismiss = {})

}
