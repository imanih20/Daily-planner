package com.mohyeddin.dialyplaner.data.room

import androidx.room.*
import com.mohyeddin.dialyplaner.domain.model.DoneWork
import com.mohyeddin.dialyplaner.domain.model.PlannedWork


@Dao
interface DoneWorkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDoneWork(doneWork: DoneWork)

    @Query("SELECT * FROM done_work WHERE date=:day")
    fun getDoneWorks(day: String) : List<DoneWork>

    @Query("SELECT * FROM done_work WHERE pid=:pid and date=:date")
    fun getDonWork(pid: Long,date: String) : DoneWork?

    @Query("DELETE FROM done_work WHERE pid=:pid and date=:date")
    suspend fun deleteDonWork(pid: Long, date: String)
}