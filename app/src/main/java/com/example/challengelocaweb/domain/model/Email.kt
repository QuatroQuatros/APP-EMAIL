package com.example.challengelocaweb.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Entity(tableName = "emails")
data class Email(
    @PrimaryKey val id: Int,
    val author: String,
    val sender: String, // Remetente
    val recipient: String, // Destinatário
    val cc: String?, // Cópia
    val bcc: String?, // Cópia Oculta
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val attachments: List<String> = listOf(),
    var isFavorite: Boolean = false,
    var isSpam: Boolean = false,
    var isSecure: Boolean = false


){
    companion object {
        fun fromJson(json: String?): Email {
            return Json.decodeFromString(json!!)
        }
    }
}