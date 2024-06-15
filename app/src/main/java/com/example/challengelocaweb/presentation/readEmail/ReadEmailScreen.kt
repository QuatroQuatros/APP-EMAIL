package com.example.challengelocaweb.presentation.readEmail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.Source
import com.example.challengelocaweb.util.convertTimestampToDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReadEmailSreen(
    email: Email
) {
    
    Column(
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(19.dp, 0.dp)
        ) {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.AutoMirrored.Outlined.ArrowBack, tint = colorResource(id = R.color.selected), contentDescription = "Voltar")
            }

            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Outlined.Delete, tint = Color.Red, contentDescription = "Deletar")
            }
        }

        Spacer(modifier = Modifier.size(19.dp))

        Column {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(modifier = Modifier.offset(12.dp, 0.dp), fontSize = 19.sp, fontWeight = FontWeight(500,), text = "Procurando por hospedagens de sites confiáveis? Venha conhecer a LocaWeb!")

            }

            Spacer(modifier = Modifier.size(15.dp))

            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Spacer(modifier = Modifier.size(10.dp))

                    Card(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logolocaweb),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(53.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 5.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        text = email.author
                                    )
                                    Text(
                                        modifier = Modifier.offset(50.dp, 0.dp),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.selected),
                                        text = convertTimestampToDate(email.publishedAt)
                                    )

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_star),
                                        contentDescription = null,
                                        tint = colorResource(id = R.color.primary),
                                        modifier = Modifier
                                            .size(24.dp)
                                            .offset(-3.dp, -5.dp)
                                    )
                                }

                                Text(
                                    fontSize = 15.sp,
                                    text = "Para mim",
                                    maxLines = 1
                                )
                            }

                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))
                }
            }

            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {

                    Card(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFCBE3E6))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(10.dp)
                        ) {

                            //Spacer(modifier = Modifier.width(10.dp))

                            Column(
                                modifier = Modifier
                                    .height(120.dp)
                                    .weight(1f)
                                    .padding(vertical = 5.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        modifier = Modifier.offset(-50.dp, 0.dp),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        text = "De:"
                                    )
                                    Text(
                                        modifier = Modifier.offset(50.dp, 0.dp),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.W400,
                                        color = colorResource(id = R.color.small_text),
                                        text = "locaweb@locaweb.com.br"
                                    )

                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Text(
                                        modifier = Modifier.offset(-16.dp, 0.dp),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        text = "Responder para"
                                    )
                                    Text(
                                        modifier = Modifier.offset(16.dp, 0.dp),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.W400,
                                        color = colorResource(id = R.color.small_text),
                                        text = "locaweb@locaweb.com.br"
                                    )

                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Text(
                                        modifier = Modifier.offset(-45.dp, 0.dp),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        text = "Cópia"
                                    )
                                    Text(
                                        modifier = Modifier.offset(45.dp, 0.dp),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.W400,
                                        color = colorResource(id = R.color.small_text),
                                        text = "fiap@fiap.com.br"
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = "Segurança",
                                        modifier = Modifier.offset(-39.dp, 0.dp)
                                    )

                                    Text(
                                        modifier = Modifier.offset(42.dp, 0.dp),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.W400,
                                        color = colorResource(id = R.color.small_text),
                                        text = "Criptografia padrão (TLS)"
                                    )
                                }
                            }

                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ReadEmailSreenPreview() {
    ReadEmailSreen(email = Email(
        author = "LocaWeb",
        content = "Descubra as possibilidades...",
        description = "Vaga para Desenvolvimento de Software...",
        publishedAt = "2024-05-01T00:00:00Z",
        source = Source(id = "id", name = "LocaWeb"),
        title = "Vaga para Desenvolvimento de Software",
        url = "",
        urlToImage = ""
    ))
}