package dev.abhaycloud.infinitescrollablecalendarview.listener

import dev.abhaycloud.infinitescrollablecalendarview.adapter.CalendarAdapter

interface OnBindListener {
    fun onBindView(holder: CalendarAdapter.CalendarViewHolder)
}