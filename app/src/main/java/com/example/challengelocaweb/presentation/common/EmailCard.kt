package com.example.challengelocaweb.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.Source
import com.example.challengelocaweb.presentation.Dimens.SmallIconSize
import com.example.challengelocaweb.ui.theme.ChallengeLocaWebTheme
import com.example.challengelocaweb.util.converteTimestampToDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmailCard(
    email: Email,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.size(15.dp))

        Card(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth(),
            //elevation = CardDefaults.cardElevation(3.dp, 3.dp, 3.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0x9AE1EBFC))
        ) {
            Row() {
                Column( modifier = Modifier) {
                    Image(
                        painterResource( id = R.drawable.mulhersorrindo),
                        "sldkf",
                        modifier = Modifier
                            .offset(x = 12.dp, y = 24.dp)
                            .size(45.dp)
                            .clip(shape = CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.size(15.dp))

                Column(
                    modifier = Modifier
                        .offset(x = 12.dp, y = 12.dp)
                        .fillMaxWidth(),
                        //.align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Center) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Text(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                text = email.author
                            )
                            Text(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id =R.color.primary),
                                text = converteTimestampToDate(email.publishedAt)
                            )

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.width(60.dp)
                            .padding(end = 40.dp),
                        horizontalArrangement = Arrangement.End
                    ){
                        Icon(
                            painterResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            tint = colorResource(id =R.color.primary),
                            modifier = Modifier.size(18.dp),
                        )
                    }


                    Text(fontSize = 11.sp, text = email.title)
                    Text(fontSize = 9.sp, text = email.content)
                }


            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmailCardPreview() {
    ChallengeLocaWebTheme {
        EmailCard(
            email = Email(
                author = "Author",
                content = "Content",
                description = "Description",
                publishedAt = "2021-09-01T00:00:00Z",
                source = Source(id = "id", name = "BATATATATATA"),
                title = "BATATA QUENTE ",
                url = "",
                urlToImage = ""
            ),
            onClick = {}
        )
    }

}