package com.example.projetolitroz.ui.home

import java.util.UUID

data class TaskWithId(
    val id: String = UUID.randomUUID().toString(), // Identificador único
    val name: String
)