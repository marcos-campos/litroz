package com.example.projetolitroz.ui.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Identificador único da tarefa
    @ColumnInfo(name = "taskName") val taskName: String, // Nome da tarefa
    @ColumnInfo(name = "isCompleted") val isCompleted: Boolean = false // Indica se a tarefa foi concluída
)
