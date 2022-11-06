package dev.abhaycloud.infinitescrollablecalendarview.listener

import dev.abhaycloud.infinitescrollablecalendarview.model.MonthModel

interface OnMonthChangedListener {
    fun onMonthChange(month: MonthModel)
}