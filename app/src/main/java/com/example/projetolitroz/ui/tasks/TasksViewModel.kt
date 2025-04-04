package com.example.projetolitroz.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase
import kotlinx.coroutines.launch
import java.util.UUID

class TasksViewModel(private val database: TasksDatabase) : ViewModel() {
    val pendingTasksLiveData = MutableLiveData<List<TaskWithId>>() // Tarefas pendentes
    val completedTasksLiveData = MutableLiveData<List<TaskWithId>>()

    private val _text = MutableLiveData<String>().apply {
        value = "Lista de Tarefas"
    }
    val text: LiveData<String> = _text

    private val _listTask = MutableLiveData<List<TaskWithId>>()
    val listTaskLiveData: LiveData<List<TaskWithId>> = _listTask

    fun getTasks() {
        viewModelScope.launch {
            val tasks = database.tasksDao().getAll()
            val taskWithIdList = tasks.map { task ->
                TaskWithId(
                    id = task.id,
                    name = task.taskName,
                    isCompleted = task.isCompleted
                )
            }

            val pendingTasks = taskWithIdList.filter { !it.isCompleted }
            val completedTasks = taskWithIdList.filter { it.isCompleted }

            pendingTasksLiveData.postValue(pendingTasks)
            completedTasksLiveData.postValue(completedTasks)
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
        val newTask = TaskWithId(
            id = 0,
            name = "Tarefa ID: ${UUID.randomUUID().toString().take(8)}",
            isCompleted = false
        )
        currentList.add(newTask)
        _listTask.value = currentList

        viewModelScope.launch {
            val taskEntity = Tasks(taskName = newTask.name, isCompleted = false)
            database.tasksDao().insertAll(taskEntity)
            getTasks()
        }
    }

    fun removeTaskFromList(task: TaskWithId) {
        val currentList = _listTask.value?.toMutableList() ?: mutableListOf()
        currentList.removeAll { it.id == task.id }
        _listTask.value = currentList
    }

    fun markTaskAsCompleted(task: TaskWithId) {
        viewModelScope.launch {
            val updatedTask = Tasks(taskName = task.name, isCompleted = true, id = task.id)
            database.tasksDao().updateTask(updatedTask)

            val currentPendingTasks = pendingTasksLiveData.value?.toMutableList() ?: mutableListOf()
            val currentCompletedTasks = completedTasksLiveData.value?.toMutableList() ?: mutableListOf()

            currentPendingTasks.removeAll { it.id == task.id }
            currentCompletedTasks.add(TaskWithId(
                id = updatedTask.id,
                name = updatedTask.taskName,
                isCompleted = updatedTask.isCompleted
            ))

            pendingTasksLiveData.postValue(currentPendingTasks)
            completedTasksLiveData.postValue(currentCompletedTasks)
        }
    }
}