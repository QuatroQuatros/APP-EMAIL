package com.example.challengelocaweb.presentation.readEmail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.util.convertTimestampToDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReadEmailScreen(
    email: Email,
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    var isFavorite by remember { mutableStateOf(email.isFavorite) }

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
                        isFavorite = !isFavorite
                        email.isFavorite = isFavorite
                        viewModel.updateEmail(email)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = if (isFavorite) R.drawable.ic_star_favorite else R.drawable.ic_star),
                        contentDescription = "Favoritar",
                        tint = if (isFavorite) colorResource(id = R.color.favorite) else colorResource(id = R.color.selected),
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
            val painter = if (!email.urlToImage.isEmpty()){
                rememberAsyncImagePainter(model = email.urlToImage)
            }else{
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
                        text = "locaweb@locaweb.com.br",
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
                        text = "locaweb@locaweb.com.br",
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
                        text = "fiap@fiap.com.br",
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ){
            Text(
                text = email.content,
                fontSize = 18.sp,
                lineHeight = 25.sp,
                textAlign = TextAlign.Justify
            )
        }


    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun ReadEmailScreenPreview() {
//    ReadEmailScreen(
//        email = Email(
//            id = 685,
//            author = "LocaWeb",
//            content = "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
//            description = "Vaga para Desenvolvimento de Software...",
//            publishedAt = "2024-05-01T00:00:00Z",
//            title = "Vaga para Desenvolvimento de Software",
//            url = "",
//            urlToImage = "https://ui-avatars.com/api/?background=random&name=LocaWeb"
//        ),
//        navController = rememberNavController() // Mocked NavController for preview
//    )
//}