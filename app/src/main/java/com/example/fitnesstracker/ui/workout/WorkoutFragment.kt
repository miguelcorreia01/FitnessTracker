package com.example.fitnesstracker.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstracker.R
import com.example.fitnesstracker.model.WorkoutEntry
import com.example.fitnesstracker.model.WorkoutSet
import com.example.fitnesstracker.viewmodel.WorkoutViewModel

class WorkoutFragment : Fragment() {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var adapter: WorkoutSetAdapter
    private lateinit var spinnerExercise: Spinner
    private lateinit var etWorkoutDate: EditText
    private lateinit var etWeight: EditText
    private lateinit var etReps: EditText

    private val sets = mutableListOf<WorkoutSet>()
    private var setCounter = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout, container, false)

        etWorkoutDate = view.findViewById(R.id.et_workout_date)
        spinnerExercise = view.findViewById(R.id.spinner_exercise)
        etWeight = view.findViewById(R.id.et_weight)
        etReps = view.findViewById(R.id.et_reps)
        val btnAddSet: Button = view.findViewById(R.id.btn_add_set)
        val btnSaveWorkout: Button = view.findViewById(R.id.btn_save_workout)
        val recyclerViewSets: RecyclerView = view.findViewById(R.id.recycler_view_sets)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        // Set up RecyclerView
        adapter = WorkoutSetAdapter { set ->
            val updatedSets = sets.toMutableList().apply { remove(set) }
            sets.clear()
            sets.addAll(updatedSets)
            adapter.submitList(sets.toList())
        }
        recyclerViewSets.adapter = adapter
        recyclerViewSets.layoutManager = LinearLayoutManager(requireContext())



        workoutViewModel.allExercises.observe(viewLifecycleOwner) { exercises ->
            val exerciseNames = exercises.map { it.name }
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, exerciseNames)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerExercise.adapter = spinnerAdapter
        }

        // Add Workout Set
        btnAddSet.setOnClickListener {
            val weight = etWeight.text.toString().toDoubleOrNull()
            val reps = etReps.text.toString().toIntOrNull()

            if (weight != null && reps != null) {
                val newSet = WorkoutSet(workoutEntryId = 0, setNumber = setCounter++, weight = weight, reps = reps)
                sets.add(newSet)
                adapter.submitList(sets.toList())
                etWeight.text.clear()
                etReps.text.clear()
            } else {
                Toast.makeText(requireContext(), "Enter valid weight and reps", Toast.LENGTH_SHORT).show()
            }
        }

        // Save Workout
        btnSaveWorkout.setOnClickListener {
            val selectedExerciseName = spinnerExercise.selectedItem.toString()
            val selectedExercise = workoutViewModel.allExercises.value?.find { it.name == selectedExerciseName }

            if (selectedExercise != null) {
                val workoutEntry = WorkoutEntry(exerciseId = selectedExercise.id, date = System.currentTimeMillis())
                workoutViewModel.insertWorkout(workoutEntry, sets)

                sets.clear()
                adapter.submitList(sets.toList())
                setCounter = 1
            } else {
                Toast.makeText(requireContext(), "Error: Selected exercise not found", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
