package com.example.challengelocaweb.presentation.events

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R
import java.time.LocalDate


// Data model for events
data class Event(
    val title: String,
    val date: String,
    val time: String,
    val description: String
)

// Sample event data (replace with your actual data)
val sampleEvents = listOf(
    Event(
        title = "Consulta Médica",
        date = "11 de Junho",
        time = "11:30",
        description = "Dr. Silva, Hospital Central"
    ),
    Event(
        title = "Reunião Jus Bezerra",
        date = "14 de Junho",
        time = "13:30",
        description = "Sala de Reuniões, 3º andar"
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen() {
    Scaffold(
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top app bar
            TopAppBar(
                title = {
                    Text(
                        text = "Calendário de Eventos",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.body)
                    )
                },
                //backgroundColor = colorResource(id = R.color.primary)
            )

            // Date header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.placeholder))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Junho 2024",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.body)
                )

                Spacer(modifier = Modifier.weight(1f)) // Push to the right

                IconButton(onClick = {}) { // TODO: Add navigation or action
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_email),
                        tint = colorResource(id = R.color.body),
                        contentDescription = "Previous month"
                    )
                }

                IconButton(onClick = {}) { // TODO: Add navigation or action
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_flag),
                        tint = colorResource(id = R.color.body),
                        contentDescription = "Next month"
                    )
                }
            }

            // Calendar grid
            CalendarGrid(events = sampleEvents)
        }
    }
}
@Composable
fun CalendarGrid(events: List<Event>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Render weekdays header
        items(listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")) { day ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.placeholder))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {

                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.body)
                )
            }
        }

        val daysInMonth = 31 // Placeholder, you'd need logic to calculate actual days
        val weeks = (daysInMonth / 7.0) // Calculate the number of weeks

        items(weeks.toInt()) { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                repeat(7) { dayIndex ->
                    val dayOfMonth = week * 7 + dayIndex + 1
                    if (dayOfMonth <= daysInMonth) {
                        val dateString = "$dayOfMonth de Junho" // Adjust date format as needed
                        val event = events.find { it.date == dateString } // Find event for this date

                        Card( // Use Card for a more elevated look
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .background(
                                    color = if (event != null)
                                    Color.Yellow else
                                    Color.Transparent,
                                    shape = MaterialTheme.shapes.small
                                )
                            //elevation = 2.dp
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (event != null) {
                                    // Display a dot or other indicator for the event
                                    Text(
                                        text = "•",
                                        color = Color.Red,
                                        fontSize = 16.sp
                                    )
                                } else {
                                    Text(
                                        text = dayOfMonth.toString(),
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.body)
                                    )
                                }
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(events: List<Event>, currentDate: LocalDate) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")) { day ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.placeholder))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {

                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.body)
                )
            }
        }
        // Render dates grid with event markers and current date highlighting
        val daysInMonth = currentDate.lengthOfMonth() // Get the number of days in the current month
        val weeks = (daysInMonth / 7.0) // Calculate the number of weeks

        items(weeks.toInt()) { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                repeat(7) { dayIndex ->
                    val dayOfMonth = week * 7 + dayIndex + 1
                    if (dayOfMonth <= daysInMonth) {
                        val dateString = "$dayOfMonth de ${currentDate.month.name}" // Adjust date format as needed
                        val event = events.find { it.date == dateString } // Find event for this date

                        Card( // Use Card for a more elevated look
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .background(
                                    color = if (event != null)
                                    Color.Yellow else
                                    Color.Transparent,
                                    shape = MaterialTheme.shapes.small
                                ),
                            //elevation = 2.dp
                            //elevation = if (currentDate.dayOfMonth == dayOfMonth) 4.dp else 2.dp // Set higher elevation for current date
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (event != null) {
                                    // Display a dot or other indicator for the event
                                    Text(
                                        text = "•",
                                        color = Color.Red,
                                        fontSize = 16.sp
                                    )
                                } else {
                                    Text(
                                        text = dayOfMonth.toString(),
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        fontWeight = if (currentDate.dayOfMonth == dayOfMonth) FontWeight.Bold else FontWeight.Normal, // Bold for current date
                                        color = colorResource(id = R.color.body)
                                    )
                                }
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun EventsScreenPreview() {
    CalendarScreen()
}

@Composable
@Preview(showBackground = true)
fun CalendarGridPreview() {
    val sampleEvents = listOf(
        Event(
            title = "Consulta Médica",
            date = "11 de Junho",
            time = "11:30",
            description = "Dr. Silva, Hospital Central"
        ),
        Event(
            title = "Reunião Jus Bezerra",
            date = "14 de Junho",
            time = "13:30",
            description = "Sala de Reuniões, 3º andar"
        )
    )
    CalendarGrid(sampleEvents)
}

