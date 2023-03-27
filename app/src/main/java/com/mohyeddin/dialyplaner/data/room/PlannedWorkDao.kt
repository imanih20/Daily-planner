package com.mohyeddin.dialyplaner.data.room

import androidx.room.*
import com.mohyeddin.dialyplaner.domain.model.PlannedWork

@Dao
interface PlannedWorkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWork(work: PlannedWork)

    @Delete
    suspend fun deleteWork(work: PlannedWork)

    @Update()
    suspend fun updateWork(work: PlannedWork)

    @Query("SELECT * FROM planned_work")
    fun getAllWorks() : List<PlannedWork>

    @Query("SELECT COUNT(*) from planned_work")
    fun countWorks() : Int
}