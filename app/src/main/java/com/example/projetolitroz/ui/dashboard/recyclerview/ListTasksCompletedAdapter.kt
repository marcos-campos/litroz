package com.example.projetolitroz.ui.dashboard.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.projetolitroz.R
import com.example.projetolitroz.ui.home.recyclerview.TasksListAdapter
import com.example.projetolitroz.ui.room.Tasks

class ListTasksCompletedAdapter(
    private val context: Context,
    private val tasksCompleted: List<Tasks>
) : RecyclerView.Adapter<ListTasksCompletedAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskDescription: TextView = view.findViewById(R.id.task_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksCompleted[position]
        holder.taskDescription.text = "ID: ${task.id} - Tarefa: ${task.taskName}" // Exibe o ID e o nome da tarefa
    }

    override fun getItemCount(): Int {
        return tasksCompleted.size
    }
}