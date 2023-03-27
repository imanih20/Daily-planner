package com.mohyeddin.dialyplaner.data.repository

import com.mohyeddin.dialyplaner.common.util.MyCalendar
import com.mohyeddin.dialyplaner.data.room.DataBase
import com.mohyeddin.dialyplaner.domain.model.DoneWork
import com.mohyeddin.dialyplaner.domain.model.PlannedWork
import com.mohyeddin.dialyplaner.domain.repository.Repository

class RepositoryImpl(private val db: DataBase) : Repository{
    override suspend fun insertWork(title: String,startTime: String, endTime: String) {
        db.plannedWorkDao().insertWork(PlannedWork(workTitle = title, startTime = startTime, endTime = endTime))
    }

    override suspend fun updateWork(plannedWork: PlannedWork) {
        db.plannedWorkDao().updateWork(plannedWork)
    }

    override suspend fun doWork(doneWork: DoneWork) {
        db.donWorkDao().insertDoneWork(doneWork)
    }

    override suspend fun unDoWork(pid: Long,date: String) {
        db.donWorkDao().deleteDonWork(pid,date)
    }

    override suspend fun deleteWork(plannedWork: PlannedWork) {
        db.plannedWorkDao().deleteWork(plannedWork)
    }

    override fun getAllPlannedWork(): List<PlannedWork> {
        return db.plannedWorkDao().getAllWorks()
    }
    override suspend fun getDailyPlan(day: String): Map<PlannedWork, Boolean> {
        val planMap = mutableMapOf<PlannedWork,Boolean>()
        getAllPlannedWork().forEach {
            val bool = isWorkDone(day,it.id)
            planMap[it] = bool
        }
        return planMap
    }

    override fun countWorks(): Int {
        return db.plannedWorkDao().countWorks()
    }

    override suspend fun isWorkDone(date: String, pid: Long): Boolean {
        val doneWork = db.donWorkDao().getDonWork(pid,date)
        return doneWork != null
    }

    override suspend fun getDayScore(date: String): Float {
        val doneWork = db.donWorkDao().getDoneWorks(date)
        val workSize = getAllPlannedWork().size
        if (doneWork.isEmpty()) return 0f
        if (workSize == 0) return 0f
        return doneWork.size.toFloat()/workSize.toFloat()
    }

    override suspend fun getWeakScore(): Float {
        val today = MyCalendar()
        var day = today.toString()
        var count = if (today.dayOfWeek == 7) 1 else today.dayOfWeek + 1
        var weakScore = 0f
        while (count>0){
            val donWorks = db.donWorkDao().getDoneWorks(day).size
            weakScore += donWorks.toFloat()/getAllPlannedWork().size.toFloat()
            count--
            day = MyCalendar(day).decreaseDay(1).toString()
        }
        return weakScore/7
    }

    override fun calculateWeak(): List<String> {
        val weak = mutableListOf<String>()
        for (i in 6 downTo 0) {
            weak.add(MyCalendar().decreaseDay(i).toString())
        }
        return weak
    }

    override fun getToday(): String {
        return MyCalendar().toString()
    }
}