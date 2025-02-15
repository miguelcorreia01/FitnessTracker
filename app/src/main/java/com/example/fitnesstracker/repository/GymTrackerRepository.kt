package com.example.fitnesstracker.repository

import androidx.lifecycle.LiveData
import com.example.fitnesstracker.model.*

class GymTrackerRepository(private val dao: FitnessTrackerDao) {

    // Exercise operations
    val allExercises: LiveData<List<Exercise>> = dao.getAllExercises()

    suspend fun insertExercise(exercise: Exercise) = dao.insertExercise(exercise)
    suspend fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise)

    // Workout Entry operations
    val allWorkoutEntries: LiveData<List<WorkoutEntry>> = dao.getAllWorkoutEntries()

    suspend fun insertWorkoutEntry(workoutEntry: WorkoutEntry): Long {
        return dao.insertWorkoutEntry(workoutEntry)
    }
    suspend fun deleteWorkoutEntry(workoutEntry: WorkoutEntry) = dao.deleteWorkoutEntry(workoutEntry)

    // Workout Set operations
    suspend fun insertWorkoutSet(workoutSet: WorkoutSet) = dao.insertWorkoutSet(workoutSet)

    fun getSetsForWorkout(entryId: Int): LiveData<List<WorkoutSet>> = dao.getSetsForWorkout(entryId)
}
