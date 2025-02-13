package com.example.fitnesstracker.repository

import androidx.lifecycle.LiveData
import com.example.fitnesstracker.model.*

class GymTrackerRepositoryRepository(private val dao: FitnessTrackerDao) {

    // Exercise operations
    val allExercises: LiveData<List<Exercise>> = dao.getAllExercises()

    suspend fun insertExercise(exercise: Exercise) = dao.insertExercise(exercise)
    suspend fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise)

    // Workout Entry operations
    val allWorkoutEntries: LiveData<List<WorkoutEntry>> = dao.getAllWorkoutEntries()

    suspend fun insertWorkoutEntry(workoutEntry: WorkoutEntry) = dao.insertWorkoutEntry(workoutEntry)

    // Workout Set operations
    suspend fun insertWorkoutSet(workoutSet: WorkoutSet) = dao.insertWorkoutSet(workoutSet)

    fun getSetsForWorkout(entryId: Int): LiveData<List<WorkoutSet>> = dao.getSetsForWorkout(entryId)
}
