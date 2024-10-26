package com.example.challengelocaweb.presentation.email

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.SendEmail
import com.example.challengelocaweb.presentation.AttachmentOption
import com.example.challengelocaweb.presentation.AttachmentOptionsDialog
import com.example.challengelocaweb.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriteEmailScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val para = remember { mutableStateOf("") }
    val cc = remember { mutableStateOf("") }
    val cco = remember { mutableStateOf("") }
    val assunto = remember { mutableStateOf("") }
    val corpo = remember { mutableStateOf("") }

    val mostrarDialogAnexo = remember { mutableStateOf(false) }
    val anexos = remember { mutableStateListOf<Uri>() }
    val contexto = LocalContext.current

    val lancadorDocumento = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            anexos.add(it)
        }
    }

    val lancadorImagem = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            anexos.add(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.new_mail),
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
                EmailTextField(label = stringResource(id = R.string.to), value = para.value) { para.value = it }
                EmailTextField(label = "CC:", value = cc.value) { cc.value = it }
                EmailTextField(label = stringResource(id = R.string.bcc), value = cco.value) { cco.value = it }
                EmailTextField(label = stringResource(id = R.string.subject), value = assunto.value) { assunto.value = it }
                EmailBodyField(value = corpo.value) { corpo.value = it }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            mostrarDialogAnexo.value = true
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
                            val conteudoHtml = """
                                <html>
                                <body>
                                    <p>De: ${para.value}</p>
                                    <p>Assunto: ${assunto.value}</p>
                                    <p>${corpo.value.replace("\n", "<br>")}</p>
                                </body>
                                </html>
                            """.trimIndent()

                            val email = SendEmail(
                                sender = "seu_email@gmail.com",
                                subject = assunto.value,
                                contentHtml = conteudoHtml,
                                contentPlain = corpo.value,
                                isConfidential = false
                            )
                            viewModel.sendEmail(email, anexos)

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
        }
    )

    if (mostrarDialogAnexo.value) {
        AttachmentOptionsDialog(
            onDismiss = { mostrarDialogAnexo.value = false },
            onOptionSelected = { opcao ->
                mostrarDialogAnexo.value = false
                when (opcao) {
                    AttachmentOption.Document -> lancadorDocumento.launch("application/pdf")
                    AttachmentOption.Camera, AttachmentOption.Gallery -> lancadorImagem.launch("image/*")
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
        Text(text = stringResource(id = R.string.write_mail), fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
