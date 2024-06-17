package com.example.challengelocaweb.domain.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.challengelocaweb.domain.model.Email
import kotlinx.coroutines.flow.Flow

@Dao
interface EmailDao {

    @Query("SELECT * FROM emails ORDER BY publishedAt DESC")
    suspend fun getEmails(): List<Email>

    @Query("SELECT * FROM emails WHERE isFavorite = 1 ORDER BY publishedAt DESC")
    fun getFavoritesEmails(): Flow<List<Email>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(emails: List<Email>)

    @Transaction
    @Query("DELETE FROM emails")
    suspend fun clearAll()

    @Delete
    suspend fun deleteEmail(email: Email)

    @Update
    suspend fun updateEmail(email: Email)


    @Transaction
    @Query("UPDATE emails SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Int)

    @Transaction
    @Query("UPDATE emails SET isRead = 0 WHERE id = :id")
    suspend fun markAsUnread(id: Int)

    @Transaction
    @Query("UPDATE emails SET isSpam = 1 WHERE id = :id")
    suspend fun markAsSpam(id: Int)

    @Transaction
    @Query("UPDATE emails SET isSecure = 1 WHERE id = :id")
    suspend fun markAsSecure(id: Int)


}
