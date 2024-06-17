package com.example.challengelocaweb.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attachments",
    foreignKeys = [ForeignKey(
        entity = Email::class,
        parentColumns = ["id"],
        childColumns = ["emailId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("emailId")]
)
data class Attachment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val emailId: Int,
    val fileName: String,
    val filePath: String
)