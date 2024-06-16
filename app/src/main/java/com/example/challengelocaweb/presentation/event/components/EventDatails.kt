package com.example.challengelocaweb.presentation.event.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengelocaweb.R
import com.example.challengelocaweb.data.repository.mocks.mockEventRepository
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.presentation.event.EventViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetails(
    event: Event,
    onClose: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(12.dp)
                        .height(12.dp)
                        .size(20.dp)
                        .background(
                            color = when(event.eventType) {
                                EventTypeEnum.MEETING -> colorResource(id = R.color.meeting)
                                EventTypeEnum.REMINDER -> colorResource(id = R.color.reminder)
                                EventTypeEnum.EVENT -> colorResource(id = R.color.event)
                            },

                            shape = RoundedCornerShape(20.dp)
                        )
                ){

                }
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.primary)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = colorResource(id = R.color.primary)
            )
            DetailRow(
                icon = painterResource(id = R.drawable.ic_time),
                text = "${event.startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${event.endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
            )
            DetailRow(
                icon = painterResource(id = R.drawable.ic_calendar),
                text = "Terça, ${event.day}"
            )
            DetailRow(
                icon = painterResource(id = R.drawable.ic_location),
                text = event.location
            )
            DetailRow(
                icon = painterResource(id = R.drawable.ic_description),
                text = event.description
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DetailRow(
                    icon = painterResource(id = R.drawable.ic_notifications),
                    text = "Me notifique",
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    modifier = Modifier.height(20.dp),
                    checked = true,//event.notify,
                    onCheckedChange = { /* TODO: Implementar lógica de notificação */ },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = colorResource(id = R.color.primary),
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.Gray
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = colorResource(id = R.color.primary)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.primary),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = colorResource(id = R.color.danger)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = colorResource(id = R.color.primary),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(onClick = onEdit) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_outline),
                            contentDescription = null,
                            tint = colorResource(id = R.color.white)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun DetailRow(icon: Painter, text: String) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.primary),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = colorResource(id = R.color.primary)
        )

    }
}
