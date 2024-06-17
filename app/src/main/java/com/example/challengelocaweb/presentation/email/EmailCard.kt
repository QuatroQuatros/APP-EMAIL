package com.example.challengelocaweb.presentation.email

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.Source
import com.example.challengelocaweb.presentation.Dimens.SmallIconSize
import com.example.challengelocaweb.presentation.home.HomeViewModel
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme
import com.example.challengelocaweb.util.convertTimestampToDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmailCard(
    email: Email,
    onClick: () -> Unit,
    viewModel: HomeViewModel
) {
    var isFavorite by remember { mutableStateOf(email.isFavorite) }
    var isRead by remember { mutableStateOf(email.isRead) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.size(10.dp))

        Card(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),

            colors = CardDefaults.cardColors(
                containerColor = if(isRead) colorResource(id = R.color.email_read) else colorResource(id = R.color.email_not_read)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {

                val painter = rememberAsyncImagePainter(model = email.urlToImage)
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
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
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.selected),
                            text = convertTimestampToDate(email.publishedAt)
                        )
                    }

                    Text(
                        fontSize = 14.sp,
                        text = email.title,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                    Text(
                        fontSize = 10.sp,
                        text = email.content,
                        maxLines = 1
                    )
                }

                IconButton(onClick = {
                    isFavorite = !isFavorite
                    email.isFavorite = isFavorite
                    viewModel.updateEmail(email)
                    }) {
                    Icon(
                        painter = if (isFavorite) painterResource(id = R.drawable.ic_star_favorite) else painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        tint = if (isFavorite) colorResource(id = R.color.favorite) else colorResource(id = R.color.selected),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
    }
}


