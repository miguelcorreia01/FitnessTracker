package com.example.fitnesstracker.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Database(entities = [Exercise::class, WorkoutEntry::class, WorkoutSet::class], version = 1, exportSchema = false)
abstract class GymTrackerDatabase : RoomDatabase() {
    abstract fun fitnessTrackerDao(): FitnessTrackerDao

    companion object {
        @Volatile
        private var INSTANCE: GymTrackerDatabase? = null

        fun getDatabase(context: Context): GymTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GymTrackerDatabase::class.java,
                    "gym_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


@Dao
interface FitnessTrackerDao {

    // Exercise Queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise_table ORDER BY name ASC")
    fun getAllExercises(): LiveData<List<Exercise>>

    // Workout Entry Queries
    @Insert
    suspend fun insertWorkoutEntry(workoutEntry: WorkoutEntry)

    @Query("SELECT * FROM workout_entry_table ORDER BY date DESC")
    fun getAllWorkoutEntries(): LiveData<List<WorkoutEntry>>

    // Workout Set Queries
    @Insert
    suspend fun insertWorkoutSet(workoutSet: WorkoutSet)

    @Query("SELECT * FROM workout_set_table WHERE workout_entry_id = :entryId")
    fun getSetsForWorkout(entryId: Int): LiveData<List<WorkoutSet>>
}
