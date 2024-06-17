package com.example.challengelocaweb.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.challengelocaweb.domain.model.Attachment


@Dao
interface AttachmentDao {

    @Query("SELECT * FROM attachments WHERE emailId = :emailId")
    suspend fun getAttachmentsForEmail(emailId: Int): List<Attachment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(attachments: List<Attachment>)

    @Query("DELETE FROM attachments WHERE emailId = :emailId")
    suspend fun deleteAttachmentsForEmail(emailId: Int)
}