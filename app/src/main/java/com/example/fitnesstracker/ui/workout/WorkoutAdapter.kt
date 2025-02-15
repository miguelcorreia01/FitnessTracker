package com.example.fitnesstracker.ui.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstracker.R
import com.example.fitnesstracker.model.WorkoutSet

class WorkoutSetAdapter(private val onDeleteClick: (WorkoutSet) -> Unit) :
    ListAdapter<WorkoutSet, WorkoutSetAdapter.WorkoutSetViewHolder>(WorkoutSetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_set, parent, false)
        return WorkoutSetViewHolder(view, onDeleteClick)
    }

    override fun onBindViewHolder(holder: WorkoutSetViewHolder, position: Int) {
        val workoutSet = getItem(position)
        holder.bind(workoutSet)
    }

    class WorkoutSetViewHolder(view: View, private val onDeleteClick: (WorkoutSet) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val tvSetNumber: TextView = view.findViewById(R.id.tv_set_number)
        private val tvWeight: TextView = view.findViewById(R.id.tv_weight)
        private val tvReps: TextView = view.findViewById(R.id.tv_reps)
        private val btnDelete: ImageButton = view.findViewById(R.id.btn_delete_set)

        fun bind(workoutSet: WorkoutSet) {
            tvSetNumber.text = "Set: ${workoutSet.setNumber}"
            tvWeight.text = "Weight: ${workoutSet.weight} kg"
            tvReps.text = "Reps: ${workoutSet.reps}"

            btnDelete.setOnClickListener {
                onDeleteClick(workoutSet)
            }
        }
    }

    class WorkoutSetDiffCallback : DiffUtil.ItemCallback<WorkoutSet>() {
        override fun areItemsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WorkoutSet, newItem: WorkoutSet): Boolean {
            return oldItem == newItem
        }
    }
}
