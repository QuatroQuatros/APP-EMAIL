package com.example.challengelocaweb.presentation.event
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.data.repository.mocks.mockEventRepository
import com.example.challengelocaweb.presentation.event.components.CreateModal
import com.example.challengelocaweb.presentation.event.components.EventDetails
import com.example.challengelocaweb.presentation.event.components.EventTypeEnum
import com.example.challengelocaweb.presentation.event.components.FloatingActionButton
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import kotlinx.coroutines.CoroutineScope
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    events: List<Event>,
    viewModel: EventViewModel
    ) {

    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var showModal by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedDay by remember { mutableStateOf(LocalDate.now().dayOfMonth) }
    var selectedMonth by remember { mutableStateOf(LocalDate.now().monthValue) }
    var selectedYear by remember { mutableStateOf(LocalDate.now().year) }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }


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
            Header(
                selectedDate = selectedDate,
                onDateChange = { newDate ->
                    selectedDate = newDate
                    selectedDay = newDate.dayOfMonth
                    selectedMonth = newDate.dayOfMonth
                    selectedYear = newDate.year
                }
            )
            WeekDaysHeader(
                selectedDate = selectedDate,
                selectedDay = selectedDay,
                onDaySelected = { day ->
                    selectedDay = day
                },
            )
            NavigationButtons(
                onPreviousClick = {
                    try {
                        selectedDate = selectedDate.minusWeeks(1)
                            .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))
                            //.with(java.time.temporal.TemporalAdjusters.firstDayOfMonth())
                        selectedDay = selectedDate.dayOfMonth
                        selectedMonth = selectedDate.monthValue
                        selectedYear = selectedDate.year
                    } catch (e: DateTimeException) {
                        selectedDay = 1
                        selectedDate = selectedDate.withDayOfMonth(1)
                        selectedMonth = selectedDate.monthValue
                        selectedYear = selectedDate.year
                    }
                },
                onNextClick = {
                    try {
                        selectedDate = selectedDate.plusWeeks(1).with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))
                        selectedDay = selectedDate.dayOfMonth
                        selectedMonth = selectedDate.monthValue
                        selectedYear = selectedDate.year
                    } catch (e: DateTimeException) {
                        selectedDay = 1
                        selectedDate = selectedDate.withDayOfMonth(1)
                        selectedMonth = selectedDate.monthValue
                        selectedYear = selectedDate.year
                    }
                }
            )
            EventsTimeline(
                selectedDay = selectedDay,
                selectedMonth = selectedMonth,
                selectedYear = selectedYear,
                events = events,
                onEventClick = { event -> selectedEvent = event }
            )

            if (showModal) {
                CreateModal(onDismiss = { showModal = false }, initialDate = selectedDate, viewModel = viewModel)
            }

            selectedEvent?.let {
                EventDetailsModal(
                    event = it,
                    onDismiss = { showBottomSheet = false; selectedEvent = null },
                    sheetState= sheetState,
                    scope = scope,
                    viewModel = viewModel
                )
            }
        }
    }
    
}



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(
    selectedDate: LocalDate,
    onDateChange: (LocalDate) -> Unit
) {
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
        val months = java.time.Month.values().map { it.getDisplayName(TextStyle.FULL, Locale("pt", "BR")).capitalize() }
        val years = (1980..2100).toList()
        var selectedMonthIndex by remember { mutableStateOf(selectedDate.monthValue - 1) }
        var selectedYearIndex by remember { mutableStateOf(years.indexOf(selectedDate.year)) }


        selectedMonthIndex = selectedDate.monthValue -1
        selectedYearIndex = years.indexOf(selectedDate.year)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownSelector(
                items = months,
                selectedIndex = selectedMonthIndex,
                onItemSelected = { index ->
                    try{
                        val newDate = selectedDate.withMonth(index + 1).withDayOfMonth(1)
                        onDateChange(newDate)
                    }catch (e: DateTimeException){
                        val newDate = selectedDate.withDayOfMonth(1)
                        onDateChange(newDate)
                    }

                },
                label = "Mês",
                labelFontSize = 20.sp
            )

            DropdownSelector(
                items = years.map { it.toString() },
                selectedIndex = selectedYearIndex,
                onItemSelected = { index ->
                    try {
                        val newDate = selectedDate.withYear(years[index])
                        onDateChange(newDate)
                    } catch (e: DateTimeException) {
                        val newDate = selectedDate.withDayOfMonth(1)
                        onDateChange(newDate)
                    }
                },
                label = "Ano",
                labelFontSize = 20.sp
            )
        }
    }
}

@Composable
fun DropdownSelector(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    label: String,
    labelFontSize: TextUnit = 12.sp,
    maxDropdownHeight: Int = 600
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable { expanded = true },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = items[selectedIndex],
                style = MaterialTheme.typography.labelLarge.copy(fontSize = labelFontSize),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary)
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Black
            )
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(100.dp)
                .background(Color.White)
                .heightIn(max = maxDropdownHeight.dp)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = { Text(text) },
                    onClick = {
                        onItemSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekDaysHeader(
    selectedDate: LocalDate,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
) {
    val startOfWeek = selectedDate.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))
    val daysOfWeek = (0 until 7).map { startOfWeek.plusDays(it.toLong()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 0.dp),
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
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        daysOfWeek.forEach { date ->
            val isSelected = date.dayOfMonth == selectedDay
            Box(
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) colorResource(id = R.color.selected)
                        else colorResource(id = R.color.primary)
                    )
                    .clickable {
                        onDaySelected(date.dayOfMonth)
                    },
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
fun EventsTimeline(
    selectedDay: Int,
    selectedMonth: Int ,
    selectedYear: Int,
    events: List<Event>,
    onEventClick: (Event) -> Unit
) {
    val selectedDate = try {
        if(selectedMonth  > 12 || selectedMonth < 1){
            LocalDate.of(selectedYear, 1, 1)
        }
        if(selectedMonth <= 12){
            LocalDate.of(selectedYear, selectedMonth, selectedDay)
        } else {
            LocalDate.of(selectedYear, 1, 1)
        }
    } catch (e: DateTimeException) {
        LocalDate.of(selectedYear, selectedMonth, 1)
    }

    val filteredEvents  = events.filter { event ->
        val eventDate = LocalDate.parse(event.createdAt.substringBefore("T"))
        eventDate == selectedDate
    }

    if (filteredEvents.isEmpty()) {
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
        ) {
            items(filteredEvents) { event ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable { onEventClick(event) }
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
                                text = event.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
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

@RequiresApi(Build.VERSION_CODES.O)
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
                if (event.startTime != LocalTime.MIN) {
                    Text(
                        text = event.startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + event.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),

                        color = colorResource(id = R.color.selected)
                    )
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetailsModal(
    event: Event,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    scope: CoroutineScope,
    viewModel: EventViewModel
) {

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState
    ){

        EventDetails(
            event = event,
            onClose = onDismiss,
            onDelete = {
                //viewModel.deleteEvent(event)
                onDismiss()
            },
            onEdit = {
                // Lógica para editar o evento
            }
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    val mockViewModel = object : EventViewModel(mockEventRepository()){}

    CalendarScreen(
        navController = rememberNavController(),
        events = listOf(
            Event(
                1,
                "Consulta Médica",
                "Levar documento e cartão SUS",
                "http://google.com",
                EventTypeEnum.MEETING,
                "11",
                LocalTime.parse("08:30"),
                LocalTime.parse("09:30"),
                false,
                false,
                "2024-06-11T00:00:00Z"
            )
        ),
        viewModel = mockViewModel
    )
}

