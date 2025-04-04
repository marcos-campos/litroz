package com.example.projetolitroz.ui.tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class TasksViewModel(private val database: TasksDatabase) : ViewModel() {
    val pendingTasksLiveData = MutableLiveData<List<TaskWithId>>()
    val completedTasksLiveData = MutableLiveData<List<TaskWithId>>()

    private val _text = MutableLiveData<String>().apply {
        value = "Lista de Tarefas"
    }
    val text: LiveData<String> = _text

    private val _listTask = MutableLiveData<List<TaskWithId>>()
    val listTaskLiveData: LiveData<List<TaskWithId>> = _listTask

    fun getTasks() {
        viewModelScope.launch {
            val tasks = withContext(Dispatchers.IO) {
                database.tasksDao().getAll()
            }
            val taskWithIdList = tasks.map { task ->
                TaskWithId(
                    id = task.id,
                    name = task.taskName,
                    isCompleted = task.isCompleted,
                    taskGoal = task.taskGoal
                )
            }

            val pendingTasks = taskWithIdList.filter { !it.isCompleted }
            val completedTasks = taskWithIdList.filter { it.isCompleted }

            pendingTasksLiveData.postValue(pendingTasks)
            completedTasksLiveData.postValue(completedTasks)
        }
    }

    fun updateTaskGoal(taskId: Int, newGoal: String) {
        viewModelScope.launch {
            val task = withContext(Dispatchers.IO) {
                database.tasksDao().getTaskById(taskId)
            }
            if (task != null) {
                val updatedTask = task.copy(taskGoal = newGoal)
                database.tasksDao().updateTask(updatedTask)
                getTasks()
            } else {
                Log.e("TasksViewModel", "Tarefa não encontrada com ID: $taskId")
            }
        }
    }

    fun addTasks(taskGoal: String) {
        val currentList = _listTask.value?.toMutableList() ?: mutableListOf()
        val newTask = TaskWithId(
            id = 0,
            name = "Tarefa ID: ${UUID.randomUUID().toString().take(8)}",
            isCompleted = false,
            taskGoal = taskGoal
        )
        currentList.add(newTask)
        _listTask.value = currentList

        viewModelScope.launch {
            val taskEntity = Tasks(taskName = newTask.name, isCompleted = false, taskGoal = taskGoal)
            database.tasksDao().insertAll(taskEntity)
            getTasks()
        }
    }

    fun removeTaskFromListById(taskId: Int) {
        viewModelScope.launch {
            database.tasksDao().deleteTaskById(taskId)
            getTasks()
        }
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
                isCompleted = updatedTask.isCompleted,
                taskGoal = task.taskGoal
            ))

            pendingTasksLiveData.postValue(currentPendingTasks)
            completedTasksLiveData.postValue(currentCompletedTasks)
        }
    }
}