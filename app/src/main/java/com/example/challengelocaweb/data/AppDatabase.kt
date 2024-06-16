package com.example.challengelocaweb.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.challengelocaweb.domain.dao.EmailDao
import com.example.challengelocaweb.domain.dao.EventDao
import com.example.challengelocaweb.domain.model.Email
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.domain.model.Participant
import com.example.challengelocaweb.util.Converters

@Database(entities = [Email::class, Event::class, Participant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){

    abstract fun emailDao(): EmailDao
    abstract fun eventDao(): EventDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
