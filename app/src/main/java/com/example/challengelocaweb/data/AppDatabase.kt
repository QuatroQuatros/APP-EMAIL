package com.example.challengelocaweb.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.challengelocaweb.domain.dao.EventDao
import com.example.challengelocaweb.domain.model.Event
import com.example.challengelocaweb.util.Converters

@Database(entities = [Event::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
//            val MIGRATION_1_2 = object : Migration(1, 2) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL("""
//                        CREATE TABLE new_events (
//                            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                            title TEXT NOT NULL,
//                            description TEXT NOT NULL,
//                            link TEXT NOT NULL,
//                            day TEXT NOT NULL,
//                            startTime TEXT,
//                            endTime TEXT,
//                            isUnique INTEGER NOT NULL,
//                            isAllDay INTEGER NOT NULL,
//                            createdAt TEXT NOT NULL
//                        )
//                    """)
//
//                    database.execSQL("""
//                        INSERT INTO new_events (id, title, description, link, day, isUnique, isAllDay, createdAt)
//                        SELECT id, title, description, link, day, 1 as isUnique, 0 as isAllDay, createdAt FROM events
//                    """)
//
//                    // 3. Remover a tabela antiga
//                    database.execSQL("DROP TABLE events")
//
//                    // 4. Renomear a nova tabela para o nome antigo
//                    database.execSQL("ALTER TABLE new_events RENAME TO events")
//                }
//            }

            val databaseName = "app_database"
            context.deleteDatabase(databaseName)

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    //.addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
