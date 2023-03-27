package com.mohyeddin.dialyplaner.data

import androidx.room.Room
import com.mohyeddin.dialyplaner.data.repository.RepositoryImpl
import com.mohyeddin.dialyplaner.data.room.DataBase
import com.mohyeddin.dialyplaner.domain.repository.Repository
import com.mohyeddin.dialyplaner.presentation.home.HomeViewModel
import com.mohyeddin.dialyplaner.presentation.plan.PlanScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "plan_db"
        ).build()
    }

    single<Repository> {
        RepositoryImpl(get())
    }

    viewModel { PlanScreenViewModel(get()) }

    viewModel { HomeViewModel(get()) }
}