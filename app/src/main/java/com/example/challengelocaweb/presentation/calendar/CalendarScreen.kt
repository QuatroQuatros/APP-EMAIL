package com.example.challengelocaweb.presentation.calendar
import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    ) {
    var showModal by remember { mutableStateOf(false) }
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
            Header()
            WeekDaysHeader()
            NavigationButtons()
            EventsTimeline()

            if (showModal) {
                CreateEventModal(onDismiss = { showModal = false })
            }
        }
    }
    
}



@Composable
fun Header() {
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
            text = "Junho 2024",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primary)
        )
    }
}

@Composable
fun WeekDaysHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { day ->
            Text(
                text = day,
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
        val today = 11
        listOf(9, 10, 11, 12, 13, 14, 15).forEach { date ->
            Box(
                Modifier
                    .size(36.dp)
                    .background(
                        color = if (date == today) Color.Red else colorResource(id = R.color.primary),
                        shape = CircleShape
                    ), Alignment.Center
            ) {
                Text(
                    text = date.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NavigationButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { /* Handle previous days navigation */ }) {
            Text("Dias anteriores")
        }
        Button(onClick = { /* Handle next days navigation */ }) {
            Text("Próximos dias")
        }
    }
}

@Composable
fun EventsTimeline() {
    val today = 11
    val events = listOf(
        Event("Reunião José Bezerra", "Sobre finanças", "09", "14:50"),
        Event("Consulta Médica", "Levar documento e cartão SUS", "11", "11:10"),
        Event("Aniversário Jully", "", "12", "00:00"),
        Event("Reunião José Bezerra", "Sobre finanças", "14", "14:50"),
        Event("Aniversário Adalberto", "", "15", "12:35"),
        Event("Aniversário Adalberto", "", "15", "12:35"),
        Event("Aniversário Adalberto", "", "15", "12:35"),
        Event("Aniversário Adalberto", "", "15", "12:35"),
        Event("Aniversário Adalberto", "", "15", "12:35"),
        Event("Chupada do Ayala", "", "69", "04:20")

        )
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
                //.background(color = Color.Blue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp)
                    //.background(color = Color.Red)
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
            //backgroundColor = colorResource(id = R.color.eventBackground)
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

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen(navController = rememberNavController())
}

