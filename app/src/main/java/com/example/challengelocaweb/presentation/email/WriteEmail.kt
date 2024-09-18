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
//    val from = remember { mutableStateOf("") }
    var to = remember { mutableStateOf("") }
    val cc = remember { mutableStateOf("") }
    val bcc = remember { mutableStateOf("") }
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

    // Launcher for selecting images
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
                            text = " ",
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
                                contentDescription = "Close"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Delete action */ }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = if (isSystemInDarkTheme()) colorResource(id = R.color.dangerDark) else colorResource(id = R.color.dangerLight),
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
                        .padding(20.dp)
                ) {

                    OutlinedTextField(
                        value = to.value,
                        onValueChange = {
                            to.value = it
                            titleError = it.isEmpty()
                        },
                        label = { Text("Para:") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        shape = RoundedCornerShape(17.dp)

                    )

                    OutlinedTextField(
                        value = cc.value,
                        onValueChange = {
                            cc.value = it
                            titleError = it.isEmpty()
                        },
                        label = { Text("Cc:") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        shape = RoundedCornerShape(17.dp)

                    )

                    OutlinedTextField(
                        value = subject.value,
                        onValueChange = {
                            subject.value = it
                            titleError = it.isEmpty()
                        },
                        label = { Text("Assunto:") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            errorBorderColor = Color.Red,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        shape = RoundedCornerShape(17.dp)

                    )


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
                                contentDescription = "Attach File"
                            )

                        }
                        FloatingActionButton(
                            onClick = {
                                val name = "Teste FIAP";
                                val sender = "fiap@fiap.com"
                                val nameNoEspace = name.replace(" ", "")
                                val currentDateTime = LocalDateTime.now()
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                                val formattedDateTime = currentDateTime.format(formatter)

                                val email = Email(
                                    author = name,
                                    sender = sender,
                                    recipient = to.value,
                                    cc = null,
                                    bcc = null,
                                    content = body.value,
                                    description = body.value,
                                    publishedAt = formattedDateTime,
                                    title = subject.value,
                                    url = "https://ui-avatars.com/api/?background=random&name=$nameNoEspace",
                                    urlToImage = "https://ui-avatars.com/api/?background=random&name=$nameNoEspace",
                                    isFavorite = false,
                                    isSpam = false,
                                    isSecure = false,
                                    isRead = false
                                )
                                viewModel.sendEmail(email, attachments)
                                navController.popBackStack()
                            },
                            containerColor = if (isSystemInDarkTheme()) colorResource(id = R.color.mainButtonsDark) else colorResource(id = R.color.primary)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_send),
                                tint = Color.White,
                                contentDescription = "Send"
                            )
                        }

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
                        AttachmentOption.Document -> {
                            documentLauncher.launch("application/pdf")
                        }
                        AttachmentOption.Camera -> {
                            imageLauncher.launch("image/*")
                        }
                        AttachmentOption.Gallery -> {
                            imageLauncher.launch("image/*")
                        }
                    }
                }
            )
        }


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

