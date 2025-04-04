package com.example.projetolitroz.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import com.example.projetolitroz.databinding.FragmentTasksBinding
import com.example.projetolitroz.ui.tasks.recyclerview.TasksListAdapter
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: TasksViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        homeViewModel.getTasks()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.getTasks()

        val buttonAddTask = binding.buttonAddTaskHome
        val editTaskGoal = binding.editTaskGoal
        buttonAddTask.setOnClickListener {
            val taskGoal = editTaskGoal.text.toString() // Captura o objetivo

            if (taskGoal.isNotEmpty()) {
                homeViewModel.addTasks(taskGoal) // Passa o nome e objetivo
            } else {
                Toast.makeText(requireContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        val recyclerTasks: RecyclerView = binding.recyclerHome
        homeViewModel.pendingTasksLiveData.observe(viewLifecycleOwner) { pendingTasks ->
            Log.d("TasksFragment", "Tarefas pendentes atualizadas: ${pendingTasks.size}")
            val adapterTasks = TasksListAdapter(
                requireContext(),
                tasks = pendingTasks,
                onTaskCompleted = { task ->
                    homeViewModel.markTaskAsCompleted(task)
                }
            )
            recyclerTasks.adapter = adapterTasks
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}