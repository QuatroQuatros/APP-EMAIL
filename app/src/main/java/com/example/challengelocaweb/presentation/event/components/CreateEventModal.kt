package com.example.challengelocaweb.presentation.event.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.challengelocaweb.data.repository.mocks.mockEventRepository
import com.example.challengelocaweb.presentation.event.EventViewModel
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateModal(
    onDismiss: () -> Unit,
    initialDate: LocalDate = LocalDate.now(),
    viewModel: EventViewModel
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    var showForm by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        if (showForm) {

            ModalForm(selectedDate = selectedDate, onDismiss = onDismiss, viewModel = viewModel)

        } else {
            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = { onDismiss() },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                selectedDate = LocalDate.ofEpochDay(millis / 86400000)
                            }
                            showForm = true
                        },
                        enabled = datePickerState.selectedDateMillis != null
                    ) {
                        Text(text = "Confirmar Data")
                    }
                },

            ) {
                DatePicker(
                    state = datePickerState,
                    title = { Text(text = "") },
                    headline = {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Selecione uma data",
                            fontSize = 25.sp
                        )
                    },
                )
            }
        }
    }
}
