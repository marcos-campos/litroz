package com.example.projetolitroz.ui.tasks.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.projetolitroz.R
import com.example.projetolitroz.ui.tasks.TaskWithId

class TasksListAdapter(
    private val context: Context,
    private val tasks: List<TaskWithId>,
    private val onTaskCompleted: (TaskWithId) -> Unit
) : RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskDescription = view.findViewById<TextView>(R.id.task_description)
        val buttonCompleted = view.findViewById<AppCompatRadioButton>(R.id.task_button_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskDescription.text = task.name

        val btn = holder.buttonCompleted
        btn.setOnClickListener {

            onTaskCompleted(task)
            Toast.makeText(context, "Tarefa ${task.name} concluída", Toast.LENGTH_SHORT).show()
        }
    }
}