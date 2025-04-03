package com.example.projetolitroz.ui.completedTasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase
import kotlinx.coroutines.launch

class CompletedTasksViewModel(private val database: TasksDatabase) : ViewModel() {

    val tasksLiveData by lazy { MutableLiveData<List<Tasks>>() }

    private val _text = MutableLiveData<String>().apply {
        value = "Tarefas concluídas"
    }
    val text: LiveData<String> = _text

    fun getCompletedTasks() {
        viewModelScope.launch {
            val tasks = database.tasksDao().getAll()
            tasksLiveData.postValue(tasks)
        }
    }
}