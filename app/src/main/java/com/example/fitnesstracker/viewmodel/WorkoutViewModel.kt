package com.example.fitnesstracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.fitnesstracker.model.*
import com.example.fitnesstracker.repository.GymTrackerRepositoryRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GymTrackerRepositoryRepository
    val allWorkoutEntries: LiveData<List<WorkoutEntry>>

    init {
        val dao = GymTrackerDatabase.getDatabase(application).fitnessTrackerDao()
        repository = GymTrackerRepositoryRepository(dao)
        allWorkoutEntries = repository.allWorkoutEntries
    }

    fun insertWorkoutEntry(workoutEntry: WorkoutEntry) = viewModelScope.launch {
        repository.insertWorkoutEntry(workoutEntry)
    }

    fun insertWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch {
        repository.insertWorkoutSet(workoutSet)
    }

    fun getSetsForWorkout(entryId: Int): LiveData<List<WorkoutSet>> {
        return repository.getSetsForWorkout(entryId)
    }
}
