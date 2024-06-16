package com.example.challengelocaweb.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.challengelocaweb.presentation.event.components.EventTypeEnum
import java.time.LocalTime

@Entity(
    tableName = "participants",
    foreignKeys = [ForeignKey(
        entity = Event::class,
        parentColumns = ["id"],
        childColumns = ["event_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("event_id")]
)
data class Participant (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val contact: String,
    val event_id: String,

)

// 1-N relationship
data class EventWithParticipants(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "id",
        entityColumn = "event_id"
    )
    val participants: List<Participant>
)



