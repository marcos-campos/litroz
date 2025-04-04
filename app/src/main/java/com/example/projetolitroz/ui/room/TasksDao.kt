package com.example.projetolitroz.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Tasks?

    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<Tasks>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg tasks: Tasks)

    @Update
    suspend fun updateTask(task: Tasks)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Int)
}