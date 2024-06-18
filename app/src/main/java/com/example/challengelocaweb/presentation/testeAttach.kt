package com.example.challengelocaweb.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.challengelocaweb.R

@Composable
fun AttachmentOptionsDialog(
    onDismiss: () -> Unit,
    onOptionSelected: (AttachmentOption) -> Unit
) {
    val options = listOf(
        AttachmentOption.Document,
        AttachmentOption.Camera,
        AttachmentOption.Gallery
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "") },
        text = {
            Column {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOptionSelected(option) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = option.icon),
                            contentDescription = option.label,
                            tint = option.color,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = option.label)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

enum class AttachmentOption(val label: String, val icon: Int, val color: Color) {
    Document("Documento", R.drawable.ic_attach, Color(0xFF9C27B0)),
    Camera("Câmera", R.drawable.ic_photo, Color(0xFFE91E63)),
    Gallery("Galeria", R.drawable.ic_image, Color(0xFF673AB7)),
}


@Preview(showBackground = true)
@Composable
fun ShowAttachModal(){
    AttachmentOptionsDialog(onDismiss = {}, onOptionSelected = {})
}