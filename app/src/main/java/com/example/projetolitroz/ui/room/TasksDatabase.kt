package com.example.projetolitroz.ui.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class], version = 2)
abstract class TasksDatabase: RoomDatabase() {

    abstract fun tasksDao(): TasksDao
}