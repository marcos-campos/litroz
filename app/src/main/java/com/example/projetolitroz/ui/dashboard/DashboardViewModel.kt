package com.example.projetolitroz.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    val tasksLiveData by lazy { MutableLiveData<List<Tasks>>() }

    private val _text = MutableLiveData<String>().apply {
        value = "Tarefas concluídas"
    }
    val text: LiveData<String> = _text

    fun getCompletedTasks(database: TasksDatabase) {
        viewModelScope.launch {
            val tasks = database.tasksDao().getAll() // Aqui você pode buscar todas as tarefas ou com base em algum filtro
            tasksLiveData.postValue(tasks)
        }
    }
}