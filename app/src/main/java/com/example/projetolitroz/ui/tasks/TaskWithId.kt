package com.example.projetolitroz.ui.tasks

import java.util.UUID

data class TaskWithId(
    val id: Int,
    val name: String,
    val isCompleted: Boolean,
    var taskGoal: String? // Adicionando o objetivo da tarefa
)