package com.mohyeddin.dialyplaner.common.util

import com.mohyeddin.dialyplaner.presentation.my_components.MyCard
import ir.huri.jcal.JalaliCalendar

class MyCalendar() : JalaliCalendar() {
    constructor(date: String) : this() {
        val dateFields = date.split('-')

        if (dateFields.size==3) {
            year = dateFields[0].toInt()
            month = dateFields[1].toInt()
            day = dateFields[2].toInt()
        }
    }
    fun decreaseDay(day: Int) : JalaliCalendar{
        return  if (this.day - day > 0)
            JalaliCalendar(year,month,this.day - day)
        else
            if (month - 1 > 0)
                JalaliCalendar(
                    year,
                    month - 1,
                    if (month - 1 < 6) 31
                    else 30
                )
            else JalaliCalendar(year-1,12,29+this.day-day)
    }

    fun increaseDay(day: Int) : JalaliCalendar {
        return if (this.day + day <= monthLength) JalaliCalendar(year,month,this.day+day)
        else
            if (month+1<=12) JalaliCalendar(year,month+1,monthLength-this.day+day)
            else JalaliCalendar(year+1,1,monthLength-this.day+day)
    }
}