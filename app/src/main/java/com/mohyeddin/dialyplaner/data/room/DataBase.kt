package com.mohyeddin.dialyplaner.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohyeddin.dialyplaner.domain.model.DoneWork
import com.mohyeddin.dialyplaner.domain.model.PlannedWork

@Database([PlannedWork::class,DoneWork::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun plannedWorkDao() : PlannedWorkDao
    abstract fun donWorkDao() : DoneWorkDao
}