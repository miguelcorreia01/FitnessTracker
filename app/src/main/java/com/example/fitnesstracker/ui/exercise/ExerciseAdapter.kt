package com.example.fitnesstracker.ui.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageButton
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstracker.R
import com.example.fitnesstracker.model.Exercise

class ExerciseAdapter(private val onDeleteClick: (Exercise) -> Unit) :
    ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }

    class ExerciseViewHolder(
        private val view: View,
        private val onDeleteClick: (Exercise) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val tvExerciseName: TextView = view.findViewById(R.id.tv_exercise_name)
        private val tvMuscleGroup: TextView = view.findViewById(R.id.tv_muscle_group)
        private val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)

        fun bind(exercise: Exercise) {
            tvExerciseName.text = exercise.name
            tvMuscleGroup.text = exercise.muscleGroup

            // Handle delete button click
            deleteButton.setOnClickListener {
                onDeleteClick(exercise)
            }
        }
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }
    }
}
