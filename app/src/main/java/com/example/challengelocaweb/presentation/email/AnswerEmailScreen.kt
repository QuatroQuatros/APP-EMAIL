package com.example.challengelocaweb.presentation.email

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengelocaweb.R

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnswerEmailScreen(
    navController: NavHostController
) {
    val from = remember { mutableStateOf("") }
    val to = remember { mutableStateOf("") }
    val cc = remember { mutableStateOf("") }
    val bcc = remember { mutableStateOf("") }
    val subject = remember { mutableStateOf("") }
    val body = remember { mutableStateOf("") }
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enviar E-mail",
                        fontSize = 24.sp,
                        color = colorResource(id = R.color.primary),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = colorResource(id = R.color.delete_color),
                            contentDescription = "Close"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Delete action */ }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = colorResource(id = R.color.danger),
                            contentDescription = "Delete"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(30.dp)
            ) {
                AnswerTextField(label = "De:", value = from.value) { from.value = it }
                AnswerTextField(label = "Para:", value = to.value) { to.value = it }
                AnswerTextField(label = "Assunto:", value = subject.value) { subject.value = it }
                AnswerBodyField(value = body.value) { body.value = it }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp)
                ) {
                    FloatingActionButton(
                        onClick = { isSheetOpen = true },
                        containerColor = colorResource(id = R.color.primary),

                        ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_attach),
                            tint = Color.White,
                            contentDescription = "Attach File"
                        )

                    }
                    FloatingActionButton(
                        onClick = { /* Send action */ },
                        containerColor = colorResource(id = R.color.primary),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_send),
                            tint = Color.White,
                            contentDescription = "Send"
                        )
                    }

                    if (isSheetOpen) {
                        BottomSheet(onDismiss = { isSheetOpen = false })
                    }

                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Enviar anexo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.selected)
            )

            Spacer(modifier = Modifier.height(46.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {

                AttachmentOption(
                    iconId = R.drawable.ic_search_document,
                    label = "Documento"
                )

                AttachmentOption(
                    iconId = R.drawable.baseline_search_24,
                    label = "Câmera",
                )

                AttachmentOption(
                    iconId = R.drawable.ic_bookmark,
                    label = "Drive",
                )
                AttachmentOption(
                    iconId = R.drawable.ic_check,
                    label = "Contatos",
                )
            }
        }
    }
}

@Composable
fun AttachmentOption(iconId: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier.size(40.dp),
            tint = colorResource(id = R.color.icon)
        )
        Text(label, color = Color.Black)
    }
}

@Preview
@Composable
private fun BottomSheetPreview() {
    BottomSheet( onDismiss = {})
}

@Composable
fun AnswerTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp),
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .padding(start = 8.dp)
                .height(25.dp),
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = true),
            keyboardActions = KeyboardActions.Default
        )
    }
}

@Composable
fun AnswerBodyField(value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "Escrever e-mail", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(200.dp),
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = true),
            keyboardActions = KeyboardActions.Default
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AnswerEmailScreenPreview(){
    AnswerEmailScreen(navController = rememberNavController())
}