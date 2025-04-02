package com.example.projetolitroz.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Tasks?

    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<Tasks>

    @Insert
    suspend fun insertAll(vararg tasks: Tasks)
}