package com.example.projetolitroz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.projetolitroz.ui.room.Tasks
import com.example.projetolitroz.ui.room.TasksDao
import com.example.projetolitroz.ui.room.TasksDatabase
import com.example.projetolitroz.ui.tasks.TaskWithId
import com.example.projetolitroz.ui.tasks.TasksViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: TasksViewModel
    private lateinit var mockDatabase: TasksDatabase
    private lateinit var mockTasksDao: TasksDao

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockDatabase = mockk()
        mockTasksDao = mockk()
        every { mockDatabase.tasksDao() } returns mockTasksDao
        viewModel = TasksViewModel(mockDatabase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addTasks should add a new task and refresh tasks`() = runTest {
        // Arrange
        coEvery { mockTasksDao.insertAll(any()) } just Runs
        coEvery { mockTasksDao.getAll() } returns emptyList()

        // Act
        viewModel.addTasks("New Task Goal")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { mockTasksDao.insertAll(match { it.taskGoal == "New Task Goal" }) }
    }

    @Test
    fun `removeTaskFromListById should delete task and refresh tasks`() = runTest {
        // Arrange
        coEvery { mockTasksDao.deleteTaskById(1) } just Runs
        coEvery { mockTasksDao.getAll() } returns emptyList()

        // Act
        viewModel.removeTaskFromListById(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { mockTasksDao.deleteTaskById(1) }
    }

    @Test
    fun `markTaskAsCompleted should update task and refresh live data`() = runTest {
        // Arrange
        val task = TaskWithId(id = 1, name = "Task 1", isCompleted = false, taskGoal = "Goal 1")
        coEvery { mockTasksDao.updateTask(any()) } just Runs

        val pendingObserver = mockk<Observer<List<TaskWithId>>>(relaxed = true)
        val completedObserver = mockk<Observer<List<TaskWithId>>>(relaxed = true)

        viewModel.pendingTasksLiveData.observeForever(pendingObserver)
        viewModel.completedTasksLiveData.observeForever(completedObserver)

        // Act
        viewModel.markTaskAsCompleted(task)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { mockTasksDao.updateTask(match { it.isCompleted }) }
        verify { pendingObserver.onChanged(match { it.isEmpty() }) }
        verify { completedObserver.onChanged(match { it.size == 1 && it[0].name == "Task 1" }) }
    }
}