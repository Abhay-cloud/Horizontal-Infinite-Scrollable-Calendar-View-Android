package dev.abhaycloud.infinitescrollablecalendarview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.abhaycloud.infinitescrollablecalendarview.adapter.CalendarAdapter
import dev.abhaycloud.infinitescrollablecalendarview.listener.CalendarCallBackListener
import dev.abhaycloud.infinitescrollablecalendarview.listener.OnBindListener
import dev.abhaycloud.infinitescrollablecalendarview.listener.OnDateSelectedListener
import dev.abhaycloud.infinitescrollablecalendarview.listener.OnMonthChangedListener
import dev.abhaycloud.infinitescrollablecalendarview.model.ItemStyles
import dev.abhaycloud.infinitescrollablecalendarview.model.MonthModel
import dev.abhaycloud.infinitescrollablecalendarview.model.SpecialDateModel
import java.time.LocalDate

/**
 * @author  Abhay
 * @created_on 2022-04-11
 * @last_updated 2022-04-11
 */

/**
 * HorizontalCalendar class
 * implements OnMonthChangedListener, OnBindListener, OnDateSelectedListener
 */
class HorizontalCalendar(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs),
    OnMonthChangedListener, OnBindListener, OnDateSelectedListener {

    /**
     * Declaring variables
     */
    private lateinit var recyclerView: RecyclerView
    private lateinit var monthTextView: TextView

    /**
     * Boolean variable which indicates title (Month and year) should get showed or not
     */
    private var showTitle: Boolean = true

    /**
     * Declaring variable of startDate (type: String)
     * which stores startDate of Calendar
     */
    private lateinit var startDate: String

    /**
     * Declaring arrayList specialDateList to store array of special dates
     */
    private lateinit var specialDateList: ArrayList<SpecialDateModel>

    /**
     * declaring variables of normalDateBg, selectedDateBg, specialDateBg of Drawable type
     */
    private lateinit var normalDateBg: Drawable
    private lateinit var selectedDateBg: Drawable
    private lateinit var specialDateBg: Drawable

    /**
     * declaring variables for textColor
     */
    private var normalDayTextColor: Int = Color.parseColor("#000000")
    private var normalDateTextColor: Int = Color.parseColor("#000000")
    private var specialDayTextColor: Int = Color.parseColor("#000000")
    private var specialDateTextColor: Int = Color.parseColor("#000000")
    private var selectedDayTextColor: Int = Color.parseColor("#000000")
    private var selectedDateTextColor: Int = Color.parseColor("#000000")


    private lateinit var calendarCallBackListener: CalendarCallBackListener

//    private var fontStyle:Typeface = ResourcesCompat.getFont(context, R.font.delius_swash_caps)!!




    init {
        LayoutInflater.from(context).inflate(R.layout.infinte_calendar_view, this, true);
        getViewElements();
        setAttributeValues(context.theme.obtainStyledAttributes(attrs, R.styleable.HorizontalRecyclerCalendar, 0, 0))
        initView();
    }

    private fun getViewElements(){
        recyclerView = findViewById(R.id.calendar_recycler_view)
        monthTextView = findViewById(R.id.textView_month)
    }

    @SuppressLint("NewApi")
    private fun setAttributeValues(a: TypedArray){
        try {
            showTitle = a.getBoolean(R.styleable.HorizontalRecyclerCalendar_showTitle, true)
            startDate = a.getString(R.styleable.HorizontalRecyclerCalendar_startDate).toString()
            normalDateBg =
                a.getDrawable(R.styleable.HorizontalRecyclerCalendar_normalDateBackground)!!
            selectedDateBg =
                a.getDrawable(R.styleable.HorizontalRecyclerCalendar_selectedDateBackground)!!
            specialDateBg =
                a.getDrawable(R.styleable.HorizontalRecyclerCalendar_specialDateBackground)!!
            normalDayTextColor = a.getColor(R.styleable.HorizontalRecyclerCalendar_normalDayTextColor, Color.BLACK)
            normalDateTextColor = a.getColor(R.styleable.HorizontalRecyclerCalendar_normalDateTextColor, Color.BLACK)
            specialDayTextColor = a.getColor(R.styleable.HorizontalRecyclerCalendar_specialDayTextColor, Color.BLACK)
            specialDateTextColor = a.getColor(R.styleable.HorizontalRecyclerCalendar_specialDateTextColor, Color.BLACK)
            selectedDayTextColor = a.getColor(R.styleable.HorizontalRecyclerCalendar_selectedDayTextColor, Color.BLACK)
            selectedDateTextColor = a.getColor(R.styleable.HorizontalRecyclerCalendar_selectedDateTextColor, Color.BLACK)
//            fontStyle = a.getFont(R.styleable.HorizontalRecyclerCalendar_dateFontStyle)!!
        }
        finally {
            a.recycle()
        }
    }

    private fun initView(){
        specialDateList = ArrayList()
        showTitle(showTitle)
        setStartDate(startDate)
        setNormalDateBackground(normalDateBg)
        setSelectedDateBackground(selectedDateBg)
        setSpecialDateBackground(specialDateBg)
        setNormalDayTextColor(normalDayTextColor)
        setNormalDateTextColor(normalDateTextColor)
        setSpecialDayTextColor(specialDayTextColor)
        setSpecialDateTextColor(specialDateTextColor)
        setSelectedDayTextColor(selectedDayTextColor)
        setSelectedDateTextColor(selectedDateTextColor)
//        setFont(fontStyle)
        initRecyclerView()
    }


    @SuppressLint("NewApi")
    private fun initRecyclerView() {
        val itemStyles = ItemStyles()
        itemStyles.normalDateBg = normalDateBg
        itemStyles.selectedDateBg = selectedDateBg
        itemStyles.specialDateBg = specialDateBg
        itemStyles.normalDayTextColor = normalDayTextColor
        itemStyles.normalDateTextColor = normalDateTextColor
        itemStyles.specialDayTextColor = specialDayTextColor
        itemStyles.specialDateTextColor = specialDateTextColor
        itemStyles.selectedDayTextColor = selectedDayTextColor
        itemStyles.selectedDateTextColor = selectedDateTextColor

        val recyclerViewLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = recyclerViewLayoutManager

        val startDate: LocalDate = LocalDate.parse(startDate)

        val  adapter = CalendarAdapter(startDate, this, specialDateList,
            R.layout.item_date_view_layout, this, itemStyles, this)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

    }

    fun showTitle(showTitle: Boolean){
        if (showTitle){
            monthTextView.visibility = View.VISIBLE
        }
        else {
            monthTextView.visibility = View.GONE
        }
    }

    fun setStartDate(start_date: String){
        if (start_date != "null"){
            startDate = start_date
        }
        else{
            startDate = "2000-01-01"
        }
        initRecyclerView()
    }

    private fun setNormalDateBackground(background: Drawable){
        normalDateBg = background

    }

    private fun setSelectedDateBackground(background: Drawable){
        selectedDateBg = background
    }

    private fun setSpecialDateBackground(background: Drawable){
        specialDateBg = background
    }

    fun setSpecialDateList(list: ArrayList<SpecialDateModel>){
        this.specialDateList = list
        initRecyclerView()
    }

    private fun setNormalDayTextColor(color: Int){
        this.normalDayTextColor = color
    }

    private fun setNormalDateTextColor(color: Int){
        this.normalDateTextColor = color
    }

    private fun setSelectedDayTextColor(color: Int){
        this.selectedDayTextColor = color
    }

    private fun setSelectedDateTextColor(color: Int){
        this.selectedDateTextColor = color
    }

    private fun setSpecialDayTextColor(color: Int){
        this.specialDayTextColor = color
    }

    private fun setSpecialDateTextColor(color: Int){
        this.specialDateTextColor = color
    }

//    private fun setFont(font: Typeface?){
//        this.fontStyle = font!!
//    }


    override fun onBindView(holder: CalendarAdapter.CalendarViewHolder) {
//        holder.day.typeface = fontStyle
//        holder.date.typeface = fontStyle
    }

    fun setOnCalendarListener(calendarCallBackListener: CalendarCallBackListener){
        this.calendarCallBackListener = calendarCallBackListener
    }

    override fun onMonthChange(month: MonthModel) {
        if (calendarCallBackListener != null){
            if (monthTextView.text !=  "${month.monthName} ${month.year}") {
                calendarCallBackListener.onMonthChange(month)
            }
        }
        monthTextView.text = "${month.monthName} ${month.year}"
    }

    override fun onDateSelected(date: LocalDate) {
        if (calendarCallBackListener != null){
            calendarCallBackListener.onDateChange(date)
        }
    }





}