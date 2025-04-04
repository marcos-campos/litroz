package com.example.projetolitroz.ui.detailsTasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetolitroz.ui.room.TasksDatabase
import com.example.projetolitroz.ui.tasks.TaskWithId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsTasksViewModel(private val database: TasksDatabase) : ViewModel() {
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

    fun removeTaskFromListById(taskId: Int) {
        viewModelScope.launch {
            database.tasksDao().deleteTaskById(taskId)
            getTasks()
        }
    }

}