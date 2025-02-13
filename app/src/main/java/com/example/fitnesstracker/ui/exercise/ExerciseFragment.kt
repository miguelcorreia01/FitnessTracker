package com.example.fitnesstracker.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstracker.R
import com.example.fitnesstracker.model.Exercise
import com.example.fitnesstracker.viewmodel.ExerciseViewModel

class ExerciseFragment : Fragment() {
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)

        // Find the views using their IDs
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val btnAdd: Button = view.findViewById(R.id.btn_add_exercise)
        val etExerciseName: EditText = view.findViewById(R.id.et_exercise_name)  // Make sure this matches the ID in the layout
        val etMuscleGroup: EditText = view.findViewById(R.id.et_muscle_group)

        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

        // Initialize Adapter
        adapter = ExerciseAdapter { exercise ->
            exerciseViewModel.deleteExercise(exercise)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe changes in the exercise list
        exerciseViewModel.allExercises.observe(viewLifecycleOwner) { exercises ->
            adapter.submitList(exercises)
        }

        // Add new exercise when the button is clicked
        btnAdd.setOnClickListener {
            val name = etExerciseName.text.toString()
            val muscleGroup = etMuscleGroup.text.toString()
            if (name.isNotEmpty() && muscleGroup.isNotEmpty()) {
                val exercise = Exercise(name = name, muscleGroup = muscleGroup)
                exerciseViewModel.insertExercise(exercise)
                etExerciseName.text.clear()
                etMuscleGroup.text.clear()
            }
        }

        return view
    }
}
