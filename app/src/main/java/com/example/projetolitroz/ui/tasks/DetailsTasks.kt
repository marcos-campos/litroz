package com.example.projetolitroz.ui.tasks

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projetolitroz.R
import com.example.projetolitroz.databinding.FragmentDetailsTasksBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsTasks : AppCompatActivity() {

    private var binding: FragmentDetailsTasksBinding? = null
    lateinit var taskName: String
    lateinit var taskGoal: String // Agora também teremos o objetivo
    lateinit var dataTasks: Bundle
    val name by lazy { findViewById<TextView>(R.id.text_details_tasks) }
    val goal by lazy { findViewById<TextView>(R.id.text_task_goal) } // Novo TextView para exibir o objetivo
    val botao by lazy { findViewById<Button>(R.id.button_delete_task) }

    private val homeViewModel: TasksViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var idTask: Int

        binding = FragmentDetailsTasksBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        intent.extras?.let {
            dataTasks = it
        }

        taskName = dataTasks.getString("taskName") as String
        taskGoal = dataTasks.getString("taskGoal") as String // Agora pegamos o objetivo da tarefa
        idTask = dataTasks.getInt("taskId") as Int

        name.text = taskName
        goal.text = taskGoal // Exibe o objetivo na tela de detalhes

        botao.setOnClickListener {
            homeViewModel.removeTaskFromListById(idTask)
            finish()
        }
    }
}