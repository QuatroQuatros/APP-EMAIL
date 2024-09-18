package com.example.challengelocaweb.presentation.event
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
                .padding(bottom = 60.dp)
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
            .padding(top = 70.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val months = java.time.Month.values().map { it.getDisplayName(TextStyle.FULL, Locale("pt", "BR")).capitalize() }
        val years = (1980..2100).toList()
        var selectedMonthIndex by remember { mutableStateOf(selectedDate.monthValue - 1) }
        var selectedYearIndex by remember { mutableStateOf(years.indexOf(selectedDate.year)) }


        selectedMonthIndex = selectedDate.monthValue -1
        selectedYearIndex = years.indexOf(selectedDate.year)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 13.dp),
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

        HorizontalDivider(Modifier.width(500.dp))
        Spacer(modifier = Modifier.height(20.dp))

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
                color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(
                    id = R.color.textLight
                )
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(
                    id = R.color.textLight
                )
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
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { day ->
            Text(
                text = day,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) colorResource(id = R.color.secondaryButtonsDark) else colorResource(
                    id = R.color.textLight
                )
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

            val backgroundColor = when {
                isSelected && isSystemInDarkTheme() -> colorResource(id = R.color.selected)
                !isSelected && isSystemInDarkTheme() -> colorResource(id = R.color.primary)
                isSelected && !isSystemInDarkTheme() -> colorResource(id = R.color.selectedDay)
                !isSelected && !isSystemInDarkTheme() -> colorResource(id = R.color.unselectedDay)
                else -> colorResource(id = R.color.primary)
            }
            Box(
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        color = backgroundColor
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

        Button(onClick = { onPreviousClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.gray) else colorResource(
                    id = R.color.navLight
                )
            )){
            Text("Dias anteriores",
                color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(
                    id = R.color.white
                ))
        }
        Button(onClick = { onNextClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.gray) else colorResource(
                    id = R.color.navLight
                )
            )) {
            Text("Próximos dias",
                color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(
                    id = R.color.white
                ))
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
                                color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(
                                    id = R.color.textLight
                                ),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,

                                )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(1f),
                                text = event.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                                color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(
                                    id = R.color.textLight
                                ),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                    TimelineEventCard(event, onEventClick)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineEventCard(
    event: Event,
    onEventClick: (Event) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(20.dp)
                    .background(color = if (isSystemInDarkTheme()) colorResource(id = R.color.gray) else colorResource(
                        id = R.color.navLight
                    ))
            )
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color = if (isSystemInDarkTheme()) colorResource(id = R.color.gray) else colorResource(
                        id = R.color.navLight
                    ))
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(20.dp)
                    .background(color = if (isSystemInDarkTheme()) colorResource(id = R.color.gray) else colorResource(
                        id = R.color.navLight
                    ))
            )
        }



        Spacer(modifier = Modifier.width(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
                .clickable { onEventClick(event) },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (event.eventType) {
                    EventTypeEnum.MEETING -> if (isSystemInDarkTheme()) colorResource(id = R.color.meetingDark) else colorResource(
                        id = R.color.meetingLight
                    )
                    EventTypeEnum.REMINDER -> if (isSystemInDarkTheme()) colorResource(id = R.color.reminderDark) else colorResource(
                        id = R.color.reminderLight
                    )
                    EventTypeEnum.EVENT -> if (isSystemInDarkTheme()) colorResource(id = R.color.eventDark) else colorResource(
                        id = R.color.eventLight
                    )
                }
            )
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Normal,
                    color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(
                        id = R.color.white
                    ),
                    fontSize = 16.sp
                )
                if (event.description.isNotEmpty()) {
                    Text(
                        text = event.description,
                        color = colorResource(id = R.color.selected),
                        fontSize = 14.sp
                    )
                }
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = event.startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + event.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
//                    color = colorResource(id = R.color.primary),
//                    fontSize = 14.sp
//                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun timeLinePreview(){
    val event = Event(
        title = "Evento 1",
        description = "Descrição do evento 1",
        startTime = LocalTime.of(10, 0),
        endTime = LocalTime.of(12, 0),
        day = "Segunda",
        location = "Local do evento 1",
        link = "http://www.google.com",
        isAllDay = false,
        isUnique = false,
        isNotifiable = false,
        eventType = EventTypeEnum.MEETING,
        createdAt = "2022-01-01T00:00:00Z"
    )
    TimelineEventCard(
        event = event,
        onEventClick = { event }
    )

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
                viewModel.deleteEvent(event)
                onDismiss()
            },
            onEdit = {
                // Lógica para editar o evento
            }
        )
    }

}
