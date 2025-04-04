package com.example.projetolitroz.ui.detailsTasks

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetolitroz.R
import com.example.projetolitroz.databinding.FragmentDetailsTasksBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsTasksActivity : AppCompatActivity() {

    private var binding: FragmentDetailsTasksBinding? = null
    lateinit var taskName: String
    lateinit var taskGoal: String
    lateinit var dataTasks: Bundle
    val name by lazy { findViewById<TextView>(R.id.text_details_tasks) }
    val goal by lazy { findViewById<TextView>(R.id.text_task_goal) }
    val editGoal by lazy { findViewById<EditText>(R.id.edit_task_goal) }
    val buttonSave by lazy { findViewById<Button>(R.id.button_save_task_goal) }
    val buttonDelete by lazy { findViewById<Button>(R.id.button_delete_task) }
    val buttonEdit by lazy { findViewById<Button>(R.id.button_edit_task) }

    private val detailsViewModel: DetailsTasksViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var idTask: Int

        binding = FragmentDetailsTasksBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        intent.extras?.let {
            dataTasks = it
        }

        taskName = dataTasks.getString("taskName") as String
        taskGoal = dataTasks.getString("taskGoal") as String
        idTask = dataTasks.getInt("taskId") as Int

        name.text = taskName
        goal.text = "Objetivo: $taskGoal"
        editGoal.setText(taskGoal)

        buttonEdit.setOnClickListener {
            buttonSave.visibility = View.VISIBLE
            editGoal.visibility = View.VISIBLE
        }

        buttonDelete.setOnClickListener {
            detailsViewModel.removeTaskFromListById(idTask)
            finish()
        }

        buttonSave.setOnClickListener {
            val newTaskGoal = editGoal.text.toString()
            if (newTaskGoal.isNotEmpty()) {
                detailsViewModel.updateTaskGoal(idTask, newTaskGoal)
                finish()
            } else {
                Toast.makeText(this, "Por favor, insira um objetivo válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}