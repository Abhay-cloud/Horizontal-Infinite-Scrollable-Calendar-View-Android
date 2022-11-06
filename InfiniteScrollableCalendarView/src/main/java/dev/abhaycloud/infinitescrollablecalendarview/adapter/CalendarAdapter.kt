package dev.abhaycloud.infinitescrollablecalendarview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import dev.abhaycloud.infinitescrollablecalendarview.R
import dev.abhaycloud.infinitescrollablecalendarview.listener.OnBindListener
import dev.abhaycloud.infinitescrollablecalendarview.listener.OnDateSelectedListener
import dev.abhaycloud.infinitescrollablecalendarview.listener.OnMonthChangedListener
import dev.abhaycloud.infinitescrollablecalendarview.model.ItemStyles
import dev.abhaycloud.infinitescrollablecalendarview.model.MonthModel
import dev.abhaycloud.infinitescrollablecalendarview.model.SpecialDateModel
import java.time.LocalDate

class CalendarAdapter(private val startDate: LocalDate, private val onMonthChangedListener: OnMonthChangedListener, private val specialDates: ArrayList<SpecialDateModel>, private val layout: Int, private val onBindListener: OnBindListener, private val itemStyles: ItemStyles,
                      private val onDateSelectedListener: OnDateSelectedListener
): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var previousPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return CalendarViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged", "NewApi")
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val dateTime: LocalDate = startDate.plusDays(position.toLong())
        onBindListener.onBindView(holder)

        holder.date.text = dateTime.dayOfMonth.toString()
        holder.day.text = dateTime.dayOfWeek.name.substring(0,3)

        holder.day.setTextColor(itemStyles.normalDayTextColor)
        holder.date.setTextColor(itemStyles.normalDateTextColor)


        onMonthChangedListener.onMonthChange(MonthModel(dateTime.month.name, dateTime.year.toString()))


//        if(dateTime.dayOfWeek.name == "SUNDAY"){
//            holder.day.setTextColor(Color.parseColor("#FFFF0000"))
//        }
//        else{
//            holder.day.setTextColor(Color.parseColor("#000000"))
//        }


        holder.itemView.setOnClickListener {
            previousPos = position
            notifyDataSetChanged()
        }


        if (previousPos == position){
//            holder.view.setBackgroundColor(Color.parseColor("#FFFFEB3B"))

            holder.day.setTextColor(itemStyles.selectedDayTextColor)
            holder.date.setTextColor(itemStyles.selectedDateTextColor)

            holder.view.background = itemStyles.selectedDateBg
            onDateSelectedListener.onDateSelected(dateTime)
        }
        else{
//            holder.view.setBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.day.setTextColor(itemStyles.normalDayTextColor)
            holder.date.setTextColor(itemStyles.normalDateTextColor)

            holder.view.background = itemStyles.normalDateBg
        }

        for (i in specialDates){
            if (previousPos == position){
//                holder.view.setBackgroundColor(Color.parseColor("#FFFFEB3B"))
                holder.day.setTextColor(itemStyles.selectedDayTextColor)
                holder.date.setTextColor(itemStyles.selectedDateTextColor)

                holder.view.background = itemStyles.selectedDateBg
            }
            else if (i.specialDate == dateTime){
                holder.day.setTextColor(itemStyles.specialDayTextColor)
                holder.date.setTextColor(itemStyles.specialDateTextColor)
                holder.view.background = itemStyles.specialDateBg
            }
        }

    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.item_tv_day)
        val date: TextView = itemView.findViewById(R.id.item_tv_date)
        val view: ConstraintLayout = itemView.findViewById(R.id.single_calendar_view)
    }

}