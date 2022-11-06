package dev.abhaycloud.infinitescrollablecalendarview.listener

import java.time.LocalDate

interface OnDateSelectedListener {
    fun onDateSelected(date: LocalDate)
}