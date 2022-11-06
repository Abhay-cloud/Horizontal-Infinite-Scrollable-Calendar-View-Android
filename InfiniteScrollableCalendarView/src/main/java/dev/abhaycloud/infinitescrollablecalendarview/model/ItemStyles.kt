package dev.abhaycloud.infinitescrollablecalendarview.model

import android.graphics.Typeface
import android.graphics.drawable.Drawable

data class ItemStyles(
    var normalDateBg: Drawable? = null,
    var selectedDateBg: Drawable? = null,
    var specialDateBg: Drawable? = null,
    var normalDayTextColor: Int = 0,
    var normalDateTextColor: Int = 0,
    var specialDayTextColor: Int = 0,
    var specialDateTextColor: Int = 0,
    var selectedDayTextColor: Int = 0,
    var selectedDateTextColor: Int = 0,
    var fontStyle: Typeface? = null
)