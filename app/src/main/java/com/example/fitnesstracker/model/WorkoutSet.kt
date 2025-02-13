package com.example.fitnesstracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_set_table",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntry::class,
            parentColumns = ["id"],
            childColumns = ["workout_entry_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutSet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "workout_entry_id") val workoutEntryId: Int,
    @ColumnInfo(name = "set_number") val setNumber: Int,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "reps") val reps: Int
)

