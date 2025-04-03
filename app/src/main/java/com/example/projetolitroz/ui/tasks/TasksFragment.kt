package com.example.projetolitroz.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projetolitroz.databinding.FragmentTasksBinding
import com.example.projetolitroz.ui.tasks.recyclerview.TasksListAdapter
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private val dataBaseTask by lazy {
        Room.databaseBuilder(
            requireContext(),
            TasksDatabase::class.java,
            "database")
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(TasksViewModel::class.java)

        homeViewModel.database = dataBaseTask

        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonAddTask = binding.buttonAddTaskHome
        buttonAddTask.setOnClickListener {
            homeViewModel.addTasks()
        }

        val recyclerTasks: RecyclerView = binding.recyclerHome

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        homeViewModel.listTaskLiveData.observe(viewLifecycleOwner) { listTasks ->
            val adapterTasks = TasksListAdapter(
                requireContext(),
                tasks = listTasks,
                onTaskCompleted = { task ->
                    val taskEntity = Tasks(taskName = task.name)
                    homeViewModel.addTasksRoom(taskEntity)
                    homeViewModel.removeTaskFromList(task)
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