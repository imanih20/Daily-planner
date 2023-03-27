package com.mohyeddin.dialyplaner.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "planned_work",
    indices = [
        Index("workTitle", unique = true)
    ])
data class PlannedWork(
    @PrimaryKey(true) val id : Long = 0,
    val workTitle: String,
    val startTime: String,
    val endTime: String
)
