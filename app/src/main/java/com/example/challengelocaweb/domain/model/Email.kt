package com.example.challengelocaweb.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Entity(tableName = "emails")
data class Email(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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
    var isDraft: Boolean = false,
    var isFavorite: Boolean = false,
    var isSpam: Boolean = false,
    var isSecure: Boolean = false,
    var isRead: Boolean = false


){
    companion object {
        fun fromJson(json: String?): Email {
            return Json.decodeFromString(json!!)
        }
    }
}

data class EmailWithAttachments(
    @Embedded val email: Email,
    @Relation(
        parentColumn = "id",
        entityColumn = "emailId"
    )
    val attachments: List<Attachment>
)

@Serializable
@Entity(tableName = "emails")
data class SendEmail(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sender: String,
    val subject: String?,
    val contentHtml: String?,
    val contentPlain: String,
    val isConfidential: Boolean = false

){
    companion object {
        fun fromJson(json: String?): SendEmail {
            return Json.decodeFromString(json!!)
        }
    }
}