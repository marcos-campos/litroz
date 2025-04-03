package com.example.projetolitroz.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projetolitroz.databinding.FragmentDashboardBinding
import com.example.projetolitroz.ui.dashboard.recyclerview.ListTasksCompletedAdapter
import com.example.projetolitroz.ui.home.HomeViewModel
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val dataBaseTask by lazy {
        Room.databaseBuilder(
            requireContext(),
            TasksDatabase::class.java,
            "database")
            .build()
    }

    private val dashboardViewModel: DashboardViewModel by lazy {
        ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerCompletedTasks: RecyclerView = binding.recyclerTasksCompleted
        val textView: TextView = binding.textDashboard

        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        dashboardViewModel.getCompletedTasks(dataBaseTask)

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