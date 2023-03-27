package com.mohyeddin.dialyplaner.domain.repository

import com.mohyeddin.dialyplaner.domain.model.DoneWork
import com.mohyeddin.dialyplaner.domain.model.PlannedWork

interface Repository {
    suspend fun insertWork(title: String,startTime: String, endTime: String)

    suspend fun updateWork(plannedWork: PlannedWork)

    suspend fun doWork(doneWork: DoneWork)

    suspend fun unDoWork(pid: Long,date: String)

    suspend fun deleteWork(plannedWork: PlannedWork)

    fun getAllPlannedWork() : List<PlannedWork>

    suspend fun getDailyPlan(day: String = getToday()) : Map<PlannedWork,Boolean>

    fun countWorks() : Int

    suspend fun isWorkDone(date: String,pid: Long) : Boolean

    suspend fun getDayScore(date: String = getToday()) : Float

    suspend fun getWeakScore() : Float

    fun calculateWeak() : List<String>

    fun getToday() : String
}