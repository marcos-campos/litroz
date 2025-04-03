package com.example.projetolitroz

import android.app.Application
import androidx.room.Room
import com.example.projetolitroz.ui.completedTasks.CompletedTasksViewModel
import com.example.projetolitroz.ui.room.TasksDatabase
import com.example.projetolitroz.ui.tasks.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.scope.get
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule) // Módulo com as definições das dependências
        }
    }
}

val appModule = module {

    single {
        Room.databaseBuilder(get(), TasksDatabase::class.java, "database")
            .fallbackToDestructiveMigration() // Fallback em caso de falha de migração
            .build()
    }

    viewModel { TasksViewModel(get()) }
    viewModel { CompletedTasksViewModel(get()) }
}