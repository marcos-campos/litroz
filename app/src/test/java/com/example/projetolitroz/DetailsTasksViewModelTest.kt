package com.example.projetolitroz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.projetolitroz.ui.detailsTasks.DetailsTasksViewModel
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
class DetailsTasksViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: DetailsTasksViewModel
    private lateinit var mockDatabase: TasksDatabase
    private lateinit var mockTasksDao: TasksDao

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockDatabase = mockk()
        mockTasksDao = mockk()
        every { mockDatabase.tasksDao() } returns mockTasksDao
        viewModel = DetailsTasksViewModel(mockDatabase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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

}