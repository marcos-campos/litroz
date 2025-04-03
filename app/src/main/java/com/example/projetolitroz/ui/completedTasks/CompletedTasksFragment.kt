package com.example.projetolitroz.ui.completedTasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projetolitroz.databinding.FragmentCompletedTasksBinding
import com.example.projetolitroz.ui.completedTasks.recyclerview.ListTasksCompletedAdapter
import com.example.projetolitroz.ui.room.TasksDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompletedTasksFragment : Fragment() {

    private var _binding: FragmentCompletedTasksBinding? = null
    private val binding get() = _binding!!

    private val dashboardViewModel: CompletedTasksViewModel by viewModel() // Injeção do ViewModel via Koin

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
    ): View? {
        _binding = FragmentCompletedTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerCompletedTasks: RecyclerView = binding.recyclerTasksCompleted

        dashboardViewModel.getCompletedTasks()

        dashboardViewModel.tasksLiveData.observe(viewLifecycleOwner) { tasks ->
            val adapterCompletedTasks = ListTasksCompletedAdapter(
                requireContext(),
                tasksCompleted = tasks
            )
            recyclerCompletedTasks.adapter = adapterCompletedTasks
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}