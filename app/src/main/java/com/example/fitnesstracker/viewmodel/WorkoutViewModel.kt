package com.example.fitnesstracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.fitnesstracker.model.*
import com.example.fitnesstracker.repository.GymTrackerRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GymTrackerRepository
    val allExercises: LiveData<List<Exercise>>
    val allWorkoutEntries: LiveData<List<WorkoutEntry>>

    init {
        val dao = GymTrackerDatabase.getDatabase(application).fitnessTrackerDao()
        repository = GymTrackerRepository(dao)
        allExercises = repository.allExercises
        allWorkoutEntries = repository.allWorkoutEntries
    }
    fun insertWorkout(workoutEntry: WorkoutEntry, sets: List<WorkoutSet>) = viewModelScope.launch {
        val workoutId = repository.insertWorkoutEntry(workoutEntry)
        sets.forEach { set ->
            repository.insertWorkoutSet(set.copy(workoutEntryId = workoutId.toInt()))
        }
    }

    fun deleteWorkoutEntry(workoutEntry: WorkoutEntry) = viewModelScope.launch {
        repository.deleteWorkoutEntry(workoutEntry)
    }


    fun getSetsForWorkout(entryId: Int): LiveData<List<WorkoutSet>> {
        return repository.getSetsForWorkout(entryId)
    }
}
