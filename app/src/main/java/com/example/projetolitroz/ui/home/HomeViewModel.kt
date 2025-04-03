package com.example.projetolitroz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel : ViewModel() {
    lateinit var database: TasksDatabase
    val tasksLiveData by lazy { MutableLiveData<List<Tasks>>() }

    private val _text = MutableLiveData<String>().apply {
        value = "Lista de Tarefas"
    }
    val text: LiveData<String> = _text

    private val _listTask = MutableLiveData<List<TaskWithId>>().apply {
//        value = arrayListOf(
//            TaskWithId(name = "Tarefa")
//        )
    }
    val listTaskLiveData: LiveData<List<TaskWithId>> = _listTask

    fun getTasks() {
        viewModelScope.launch {
            val tasks  = database.tasksDao().getAll()
            tasksLiveData.postValue(tasks)
        }
    }

    fun addTasksRoom(task: Tasks) {
        viewModelScope.launch {
            database.tasksDao().insertAll(task)
            getTasks()
        }
    }

    fun getTaskById(taskId: Int) {
        viewModelScope.launch {
            val task = database.tasksDao().getTaskById(taskId)
        }
    }

    fun addTasks() {
        val currentList = _listTask.value?.toMutableList() ?: mutableListOf()
        val newTask = TaskWithId(name = "Tarefa ID: ${UUID.randomUUID().toString().take(8)}")
        currentList.add(newTask)
        _listTask.value = currentList
    }

    fun removeTaskFromList(task: TaskWithId) {
        val currentList = _listTask.value?.toMutableList() ?: mutableListOf()
        currentList.remove(task) // Remove a tarefa da lista
        _listTask.value = currentList // Atualiza o LiveData
    }

}