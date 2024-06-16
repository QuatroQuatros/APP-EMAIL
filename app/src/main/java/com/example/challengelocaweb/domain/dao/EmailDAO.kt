package com.example.challengelocaweb.domain.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.challengelocaweb.domain.model.Email

@Dao
interface EmailDao {
//    @Query("SELECT * FROM emails ORDER BY publishedAt DESC")
//    fun getEmails(): PagingSource<Int, Email>
//
//    @Query("SELECT * FROM emails ORDER BY publishedAt DESC")
//    suspend fun getEmailsList(): List<Email>

    @Query("SELECT * FROM emails ORDER BY publishedAt DESC")
    suspend fun getEmails(): List<Email>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(emails: List<Email>)

    @Query("DELETE FROM emails")
    suspend fun clearAll()

    @Delete
    suspend fun deleteEmail(email: Email)

    @Update
    suspend fun updateEmail(email: Email)
}
