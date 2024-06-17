package com.example.challengelocaweb.presentation.email



import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Attachment
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.util.convertTimestampToDate
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReadEmailScreen(
    emailId: Int,
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current
    val emailWithAttachments by viewModel.getEmailWithAttachments(emailId).collectAsState(initial = null)
    emailWithAttachments?.let { emailData ->
        val email = emailData.email
        val attachments = emailData.attachments
        var isFavorite by remember { mutableStateOf(email.isFavorite) }
        var isRead by remember { mutableStateOf(email.isRead) }
        var isSpam by remember { mutableStateOf(email.isSpam) }
        val snackbarHostState = remember { SnackbarHostState() }
        var isButtonEnabled by remember { mutableStateOf(true) }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        snackbar = { data ->
                            Snackbar(
                                modifier = Modifier.align(Alignment.Center),
                                snackbarData = data,
                                containerColor = colorResource(id = R.color.primary),
                                contentColor = Color.White
                            )
                        }
                    )
                }
            },
            content = {
                Column(
                    modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Outlined.ArrowBack,
                                tint = colorResource(id = R.color.selected),
                                contentDescription = "Voltar"
                            )
                        }

                        Row {
                            IconButton(onClick = { /* Responder */ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_reply),
                                    contentDescription = "Responder",
                                    tint = colorResource(id = R.color.primary)
                                )
                            }
                            IconButton(onClick = { /* Encaminhar */ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_forward),
                                    contentDescription = "Encaminhar",
                                    tint = colorResource(id = R.color.primary)
                                )
                            }
                            IconButton(onClick = { /* Arquivar */ }) {
                                Icon(
                                    imageVector = Icons.Rounded.MailOutline,
                                    contentDescription = "Arquivar",
                                    tint = colorResource(id = R.color.primary)
                                )
                            }

                            IconButton(
                                onClick = {
                                    if (isButtonEnabled) {
                                        isButtonEnabled = false
                                        if (isSpam) {
                                            isSpam = !isSpam
                                            viewModel.markAsNotSpam(email.id)
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Email marcado como não spam")
                                                isButtonEnabled = true
                                            }
                                        } else {
                                            isSpam = !isSpam
                                            viewModel.markAsSpam(email.id)
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Email marcado como spam")
                                                isButtonEnabled = true
                                            }
                                        }
                                    }
                                },
                            ) {
                                Icon(
                                    painter = if (isSpam) painterResource(id = R.drawable.ic_check) else painterResource(
                                        id = R.drawable.ic_spam
                                    ),
                                    contentDescription = "SPAM",
                                    tint = colorResource(id = R.color.primary)
                                )
                            }

                            IconButton(
                                onClick = {
                                    if (isButtonEnabled) {
                                        isButtonEnabled = false
                                        if (isRead) {
                                            isRead = !isRead
                                            viewModel.markAsUnread(email.id)
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Email marcado como não lido")
                                                isButtonEnabled = true
                                            }
                                        } else {
                                            isRead = !isRead
                                            viewModel.markAsRead(email.id)
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Email marcado como lido")
                                                isButtonEnabled = true
                                            }
                                        }
                                    }
                                },
                                enabled = isButtonEnabled
                            ) {
                                Icon(
                                    painter = if (isRead) painterResource(id = R.drawable.ic_mark_unread) else painterResource(
                                        id = R.drawable.ic_mark_read
                                    ),
                                    contentDescription = "",
                                    tint = colorResource(id = R.color.primary)
                                )
                            }

                            IconButton(
                                onClick = {
                                    isFavorite = !isFavorite
                                    email.isFavorite = isFavorite
                                    viewModel.updateEmail(email)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = if (isFavorite) R.drawable.ic_star_favorite else R.drawable.ic_star),
                                    contentDescription = "Favoritar",
                                    tint = if (isFavorite) colorResource(id = R.color.favorite) else colorResource(
                                        id = R.color.selected
                                    ),
                                )
                            }
                            IconButton(onClick = {
                                viewModel.deleteEmail(email)
                                navController.popBackStack()
                            }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    tint = Color.Red,
                                    contentDescription = "Deletar"
                                )
                            }
                        }
                    }

                    Text(
                        text = email.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        val painter = if (email.urlToImage.isNotEmpty()) {
                            rememberAsyncImagePainter(model = email.urlToImage)
                        } else {
                            painterResource(id = R.drawable.logolocaweb)
                        }
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 5.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = email.author,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = convertTimestampToDate(email.publishedAt),
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.selected)
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "De:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = email.sender,
                                    fontSize = 14.sp,
                                    color = colorResource(id = R.color.small_text)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Responder para:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = email.sender,
                                    fontSize = 14.sp,
                                    color = colorResource(id = R.color.small_text)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Cópia:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = email.cc ?: "N/A",
                                    fontSize = 14.sp,
                                    color = colorResource(id = R.color.small_text)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "Segurança"
                                )
                                Text(
                                    text = "Criptografia padrão (TLS)",
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.small_text)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                        //verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = email.content,
                            fontSize = 18.sp,
                            lineHeight = 25.sp,
                            textAlign = TextAlign.Justify
                        )

                        if (attachments.isNotEmpty()) {
                            attachments.forEach { attachment ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable{

                                            downloadAttachment(context, attachment)
                                        }
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_download),
                                        contentDescription = "Download",
                                        tint = colorResource(id = R.color.primary),
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = attachment.fileName,
                                        fontSize = 14.sp
                                    )
                                }

                            }
                        }

                    }

                }
            }
        )
    }
}

private fun downloadAttachment(context: Context, attachment: Attachment) {
    val request = DownloadManager.Request(Uri.parse(attachment.filePath))
        .setTitle(attachment.fileName)
        .setDescription("Baixando anexo")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, attachment.fileName)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}

