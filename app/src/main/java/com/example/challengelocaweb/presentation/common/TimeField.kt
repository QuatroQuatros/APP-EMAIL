package com.example.challengelocaweb.presentation.common

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.challengelocaweb.R
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePicker(
    context: Context,
    label: String,
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit
) {
    val time = remember { mutableStateOf(initialTime) }
    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hour: Int, minute: Int ->
            val selectedTime = LocalTime.of(hour, minute)
            time.value = selectedTime
            onTimeSelected(selectedTime)
        },
        time.value.hour,
        time.value.minute,
        true
    )

    OutlinedTextField(
        value = time.value.toString(),
        onValueChange = {},
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = colorResource(id = R.color.primary)
            )
        },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { timePickerDialog.show() }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TimeDropdownSelectorPreview(){

    TimePicker(
        context = LocalContext.current,
        label = "teste",
        initialTime = LocalTime.now(),
        onTimeSelected = {}
    )
//    TimeDropdownSelector(
//        value = LocalTime.now(),
//        onValueChange = {},
//        label = "teste"
//    )
}



//@Composable
//fun TimeField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: @Composable (() -> Unit),
//    leadingIcon: @Composable (() -> Unit),
//    modifier: Modifier = Modifier
//) {
//    val formattedValue = remember(value) {
//        formatTimeInput(value)
//    }
//
//    OutlinedTextField(
//        value = formattedValue,
//        onValueChange = { newValue ->
//            val cleanedValue = newValue.filter { it.isDigit() }
//            if (cleanedValue.length <= 4) {
//                onValueChange(cleanedValue)
//            }
//        },
//        label = label,
//        leadingIcon = leadingIcon,
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Number,
//            imeAction = ImeAction.Done
//        ),
//        visualTransformation = TimeVisualTransformation(),
//        modifier = modifier,
//    )
//}
//
//fun formatTimeInput(input: String): String {
//    return input.padEnd(4, '0').take(4).chunked(2).joinToString(":")
//}
//
//class TimeVisualTransformation : VisualTransformation {
//    override fun filter(text: AnnotatedString): TransformedText {
//        val inputText = text.text.padEnd(4, '0').take(4).chunked(2).joinToString(":")
//        val offsetMapping = object : OffsetMapping {
//            override fun originalToTransformed(offset: Int): Int {
//                return if (offset <= 2) offset else offset + 1
//            }
//
//            override fun transformedToOriginal(offset: Int): Int {
//                return if (offset <= 2) offset else offset - 1
//            }
//        }
//        return TransformedText(AnnotatedString(inputText), offsetMapping)
//    }
//}
