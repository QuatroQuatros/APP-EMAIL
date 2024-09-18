package com.example.challengelocaweb.presentation.email

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.SendEmail
import com.example.challengelocaweb.presentation.AttachmentOption
import com.example.challengelocaweb.presentation.AttachmentOptionsDialog
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriteEmailScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val to = remember { mutableStateOf("") }
    val cc = remember { mutableStateOf("") }
    val cco = remember { mutableStateOf("") }
    val subject = remember { mutableStateOf("") }
    val body = remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }

    val showAttachmentDialog = remember { mutableStateOf(false) }
    val attachments = remember { mutableStateListOf<Uri>() }
    val context = LocalContext.current

    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            attachments.add(it)
        }
    }

    // Launcher para selecionar imagens
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            attachments.add(it)
        }
    }
    ChallengeLocaWebTheme(dynamicColor = true, darkTheme = isSystemInDarkTheme()) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Novo Email",
                        fontSize = 22.sp,
                        color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.textLight),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = if (isSystemInDarkTheme()) colorResource(id = R.color.tertiaryDetailsDark) else colorResource(id = R.color.textLight),
                            contentDescription = "Fechar"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Ação de deletar */ }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = if (isSystemInDarkTheme()) colorResource(id = R.color.dangerDark) else colorResource(id = R.color.dangerLight),
                            contentDescription = "Deletar"
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
                EmailTextField(label = "Para:", value = to.value) { to.value = it }
                EmailTextField(label = "CC:", value = cc.value) { cc.value = it }
                EmailTextField(label = "CCO:", value = cco.value) { cco.value = it }
                EmailTextField(label = "Assunto:", value = subject.value) { subject.value = it }
                EmailBodyField(value = body.value) { body.value = it }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            showAttachmentDialog.value = true
                        },
                        containerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.secondaryButtonsDark) else colorResource(id = R.color.secondaryButtonsLight),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_attach),
                            tint = Color.White,
                            contentDescription = "Anexar arquivo"
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            val htmlContent = """
                                <html>
                                <body>
                                    <p>De: ${to.value}</p>
                                    <p>Assunto: ${subject.value}</p>
                                    <p>${body.value.replace("\n", "<br>")}</p>
                                </body>
                                </html>
                            """.trimIndent()

                            // Enviar e-mail
                            val email = SendEmail(
                                sender = "seu_email@gmail.com",
                                subject = subject.value,
                                contentHtml = htmlContent,
                                contentPlain = body.value,
                                isConfidential = false
                            )
                            viewModel.sendEmail(email, attachments)

                            navController.popBackStack()
                        },
                        containerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.mainButtonsDark) else colorResource(id = R.color.mainButtonsLight)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_send),
                            tint = Color.White,
                            contentDescription = "Enviar"
                        )
                    }
                }
            }
        )

    if (showAttachmentDialog.value) {
        AttachmentOptionsDialog(
            onDismiss = { showAttachmentDialog.value = false },
            onOptionSelected = { option ->
                showAttachmentDialog.value = false
                when (option) {
                    AttachmentOption.Document -> documentLauncher.launch("application/pdf")
                    AttachmentOption.Camera, AttachmentOption.Gallery -> imageLauncher.launch("image/*")
                }
            }
        )
    }
}


@Composable
fun EmailTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 7.dp)
            .background(if (isSystemInDarkTheme()) colorResource(id = R.color.gray) else colorResource(id = R.color.lighBlue), shape = RoundedCornerShape(10.dp))
            .border(
                BorderStroke(
                    1.dp,
                    color = if (isSystemInDarkTheme()) colorResource(id = R.color.gray) else colorResource(
                        id = R.color.lighBlue
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .height(40.dp)
    ) {
        Text(
            modifier = Modifier.padding(9.dp),
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
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
fun EmailBodyField(value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            modifier = Modifier.padding(top = 9.dp),
            text = "Escrever e-mail",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .height(200.dp),
            textStyle = TextStyle(fontSize = 16.sp, color = if (isSystemInDarkTheme()) colorResource(id = R.color.textDark) else colorResource(id = R.color.small_text)),
            cursorBrush = SolidColor(if (isSystemInDarkTheme()) Color.White else Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(autoCorrect = true),
            keyboardActions = KeyboardActions.Default
        )
    }
}

