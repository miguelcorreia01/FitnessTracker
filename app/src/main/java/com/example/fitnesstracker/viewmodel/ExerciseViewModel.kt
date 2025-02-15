package com.example.fitnesstracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.fitnesstracker.model.Exercise
import com.example.fitnesstracker.repository.GymTrackerRepository
import com.example.fitnesstracker.model.GymTrackerDatabase
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GymTrackerRepository

    val allExercises: LiveData<List<Exercise>>


    init {
        val dao = GymTrackerDatabase.getDatabase(application).fitnessTrackerDao()
        repository = GymTrackerRepository(dao)
        allExercises = repository.allExercises
    }

    fun insertExercise(exercise: Exercise) = viewModelScope.launch {
        repository.insertExercise(exercise)
    }

    fun deleteExercise(exercise: Exercise) = viewModelScope.launch {
        repository.deleteExercise(exercise)
    }
}
