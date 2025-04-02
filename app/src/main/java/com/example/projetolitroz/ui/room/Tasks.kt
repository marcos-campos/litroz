package com.example.projetolitroz.ui.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "task_name") val taskName: String = "Teste"
)
