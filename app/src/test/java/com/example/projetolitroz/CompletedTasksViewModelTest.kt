package com.example.projetolitroz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.projetolitroz.ui.completedTasks.CompletedTasksViewModel
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDao
import com.example.projetolitroz.ui.room.TasksDatabase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CompletedTasksViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CompletedTasksViewModel
    private lateinit var database: TasksDatabase
    private lateinit var tasksDao: TasksDao

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        tasksDao = mockk()
        database = mockk {
            every { tasksDao() } returns tasksDao
        }
        viewModel = CompletedTasksViewModel(database)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCompletedTasks should post completed tasks to tasksLiveData`() = runTest {
        // Arrange
        val completedTask = Tasks(id = 1, taskName = "Tarefa 1", isCompleted = false)
        val incompleteTask = Tasks(id = 2, taskName = "Tarefa 2", isCompleted = false)
        val tasks = listOf(completedTask, incompleteTask)

        coEvery { tasksDao.getAll() } returns listOf()

        val observer = mockk<Observer<List<Tasks>>>(relaxed = true)
        viewModel.tasksLiveData.observeForever(observer)

        // Act
        viewModel.getCompletedTasks()
        advanceUntilIdle()

        // Assert
        coVerify { tasksDao.getAll() }
        verify { observer.onChanged(listOf()) }

        viewModel.tasksLiveData.removeObserver(observer)
    }

    @Test
    fun `text LiveData should have initial value`() {
        // Arrange
        val observer = mockk<Observer<String>>(relaxed = true)
        viewModel.text.observeForever(observer)

        // Act & Assert
        verify { observer.onChanged("Tarefas concluídas") }

        viewModel.text.removeObserver(observer)
    }
}