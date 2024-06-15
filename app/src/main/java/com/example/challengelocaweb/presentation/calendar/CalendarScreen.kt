package com.example.challengelocaweb.presentation.calendar
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import com.example.challengelocaweb.domain.model.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import java.time.format.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.presentation.common.TimelineEventCard
import com.example.challengelocaweb.presentation.calendar.components.CreateEventModal
import com.example.challengelocaweb.presentation.calendar.components.FloatingActionButton
import java.time.LocalDate
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    ) {
    var showModal by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedDay by remember { mutableStateOf(LocalDate.now().dayOfMonth) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showModal = true })
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
        ) {
            Header(selectedDate = selectedDate)
            WeekDaysHeader(selectedDate, selectedDay) { day ->
                selectedDay = day
                selectedDate = selectedDate.withDayOfMonth(day)
            }
            NavigationButtons(
                onPreviousClick = {
                    selectedDate = selectedDate.minusWeeks(1).with(java.time.DayOfWeek.SUNDAY)
                    selectedDay = selectedDate.dayOfMonth
                },
                onNextClick = {
                    selectedDate = selectedDate.plusWeeks(1).with(java.time.DayOfWeek.MONDAY)
                    selectedDay = selectedDate.dayOfMonth
                }
            )
            EventsTimeline(selectedDate = selectedDate)

            if (showModal) {
                CreateEventModal(onDismiss = { showModal = false })
            }
        }
    }
    
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(selectedDate: LocalDate) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Meus Eventos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primary)
        )
        Text(
            text = "${selectedDate.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR")).capitalize()} ${selectedDate.year}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primary)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekDaysHeader(
    selectedDate: LocalDate,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    val startOfWeek = selectedDate.with(java.time.DayOfWeek.MONDAY)
    val daysOfWeek = (0 until 7).map { startOfWeek.plusDays(it.toLong()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val daysOfWeekShortNames = listOf("D", "S", "T", "Q", "Q", "S", "S")
        daysOfWeekShortNames.forEachIndexed { index, dayShortName ->
            Text(
                text = dayShortName,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary)
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val today = LocalDate.now()
        daysOfWeek.forEach { date ->
            Box(
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        color = when {
                            date.dayOfMonth == selectedDay -> colorResource(id = R.color.selected)
                            date == today -> colorResource(id = R.color.primary)
                            else -> colorResource(id = R.color.primary)
                        }
                    )
                    .clickable { onDaySelected(date.dayOfMonth) },
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = date.dayOfMonth.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NavigationButtons(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { onPreviousClick() }){
            Text("Dias anteriores")
        }
        Button(onClick = { onNextClick() }) {
            Text("Próximos dias")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventsTimeline(selectedDate: LocalDate) {
    val listEvents = listOf(
        Event("Reunião José Bezerra", "Sobre finanças", "09", "14:50"),
        Event("Consulta Médica", "Levar documento e cartão SUS", "11", "11:10"),
        Event("Aniversário Jully", "", "12", "00:00"),
        Event("Reunião José Bezerra", "Sobre finanças", "14", "14:50"),
        Event("Aniversário Adalberto", "", "11", "12:35"),
        Event("Aniversário Adalberto", "", "12", "12:35"),
        Event("Aniversário Adalberto", "", "13", "12:35"),
        Event("Aniversário Adalberto", "", "14", "12:35"),
        Event("Aniversário Adalberto", "", "15", "12:35"),
        Event("Chupada do Ayala", "", "16", "04:20")

        )

    val events = listEvents.filter { event ->
        val eventDate = LocalDate.of(selectedDate.year, selectedDate.month, event.day.toInt())
        eventDate == selectedDate
    }

    if (events.isEmpty()) {
        Text(
            text = "Nenhum evento para o dia selecionado.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = colorResource(id = R.color.placeholder),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
            //.background(color = Color.Green)
        ) {
            items(events) { event ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 0.dp, top = 20.dp)
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxHeight(1f),
                                text = event.day,
                                color = colorResource(id = R.color.selected),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,

                                )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(1f),
                                text = event.time,
                                color = colorResource(id = R.color.selected),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                    TimelineEventCard(event)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}

@Composable
fun TimelineEventCard(event: Event) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box() {

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.primary))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .background(color = colorResource(id = R.color.primary))
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.primary)
                )
                if (event.description.isNotEmpty()) {
                    Text(text = event.description)
                }
                if (event.time.isNotEmpty()) {
                    Text(
                        text = event.time,
                        color = colorResource(id = R.color.selected)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen(navController = rememberNavController())
}

