package com.mohyeddin.dialyplaner.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "done_work",
    foreignKeys = [
        ForeignKey(
            PlannedWork::class,
            ["id"],
            ["pid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["pid"]
        )
    ]
)
data class DoneWork(
    @PrimaryKey(true) val id : Long = 0,
    val pid: Long,
    val date: String
)
