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
    lateinit var dataTasks: Bundle
    val name by lazy { findViewById<TextView>(R.id.text_details_tasks) }
    val id by lazy { findViewById<TextView>(R.id.id_details_tasks) }
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
        idTask = dataTasks.getInt("taskId") as Int
        name.text = taskName

        botao.setOnClickListener {
            homeViewModel.removeTaskFromListById(idTask)
            finish()
        }

    }
}