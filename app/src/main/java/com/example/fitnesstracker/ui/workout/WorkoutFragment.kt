package com.example.fitnesstracker.ui.workout

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
import com.example.fitnesstracker.model.WorkoutEntry
import com.example.fitnesstracker.model.WorkoutSet
import com.example.fitnesstracker.viewmodel.WorkoutViewModel

class WorkoutFragment : Fragment() {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var adapter: WorkoutSetAdapter
    private val sets = mutableListOf<WorkoutSet>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_workout, container, false)

        val etWeight: EditText = view.findViewById(R.id.et_weight)
        val etReps: EditText = view.findViewById(R.id.et_reps)
        val rvSets: RecyclerView = view.findViewById(R.id.rv_sets)
        val btnAddSet: Button = view.findViewById(R.id.btn_add_set)
        val btnSaveWorkout: Button = view.findViewById(R.id.btn_save_workout)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        adapter = WorkoutSetAdapter(sets)
        rvSets.layoutManager = LinearLayoutManager(requireContext())
        rvSets.adapter = adapter

        btnAddSet.setOnClickListener {
            val weight = etWeight.text.toString().toDoubleOrNull()
            val reps = etReps.text.toString().toIntOrNull()

            if (weight != null && reps != null) {
                val set = WorkoutSet(workoutEntryId = 0, setNumber = sets.size + 1, weight = weight, reps = reps)
                sets.add(set)
                adapter.notifyDataSetChanged()
            }
        }

        btnSaveWorkout.setOnClickListener {
            val workoutEntry = WorkoutEntry(exerciseId = 1, date = System.currentTimeMillis())
            workoutViewModel.insertWorkoutEntry(workoutEntry)
        }

        return view
    }
}
