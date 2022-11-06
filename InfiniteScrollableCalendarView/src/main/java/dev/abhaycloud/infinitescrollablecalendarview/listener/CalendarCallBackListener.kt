package dev.abhaycloud.infinitescrollablecalendarview.listener

import dev.abhaycloud.infinitescrollablecalendarview.model.MonthModel
import java.time.LocalDate

interface CalendarCallBackListener {

    fun onDateChange(date: LocalDate)

    fun onMonthChange(month: MonthModel)

}