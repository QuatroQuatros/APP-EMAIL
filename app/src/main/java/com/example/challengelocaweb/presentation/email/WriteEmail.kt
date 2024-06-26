package com.example.challengelocaweb.presentation.email

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.core.Camera
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.challengelocaweb.domain.model.Attachment
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.AttachmentOption
import com.example.challengelocaweb.presentation.AttachmentOptionsDialog
import com.example.challengelocaweb.presentation.home.HomeViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriteEmailScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
//    val from = remember { mutableStateOf("") }
    val to = remember { mutableStateOf("") }
    val cc = remember { mutableStateOf("") }
    val bcc = remember { mutableStateOf("") }
    val subject = remember { mutableStateOf("") }
    val body = remember { mutableStateOf("") }

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
//                EmailTextField(label = "De:", value = from.value) { from.value = it }
                EmailTextField(label = "Para:", value = to.value) { to.value = it }
                //EmailTextField(label = "Cc:", value = cc.value) { cc.value = it }
                //EmailTextField(label = "Cco:", value = bcc.value) { bcc.value = it }
                EmailTextField(label = "Assunto:", value = subject.value) { subject.value = it }
                EmailBodyField(value = body.value) { body.value = it }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 80.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                             showAttachmentDialog.value = true
                        },
                        containerColor = colorResource(id = R.color.primary),

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
                        containerColor = colorResource(id = R.color.primary),
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

@Composable
fun EmailTextField(label: String, value: String, onValueChange: (String) -> Unit) {
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
fun EmailBodyField(value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "Escrever e-mail",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
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

