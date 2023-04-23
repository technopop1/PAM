package pl.edu.uwr.pum.gardenway.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.pum.gardenway.R
import java.time.LocalDate

internal class CalendarAdapter(
    private val taskViewModel: CalendarViewModel,
    private val daysOfMonth: ArrayList<String>,
    private val monthYearText : TextView?,
    private val onItemListener: OnItemListener,
//    calendarComparator: CalendarComparator
) //: ListAdapter<CalendarNoteEntity, CalendarViewHolder>(calendarComparator) {
    : RecyclerView.Adapter<CalendarViewHolder>(){

    lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()

        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]
        if (daysOfMonth[position] != "") {
//            val calendarNoteByDate = taskViewModel.getCalendarNoteByDate(
//                daysOfMonth[position] + "/" + monthYearText.toString().replace(' ', '/')
//            )
//            println()
//            if (calendarNoteByDate != null && calendarNoteByDate.value != null
//                && calendarNoteByDate.value!!.isNotEmpty()
//            ){
//                println(calendarNoteByDate.value?.get(0)?.noteDate)
//                val cellDayLayout : LinearLayout = view.findViewById(R.id.cellDayLayout)
//                cellDayLayout.setBackgroundColor(R.color.blue)
//            }
            // PRZENIESC DO CREATE
        }
//        holder.bind(taskViewModel)
//        if (taskViewModel != null)
//            taskViewModel.insert(CalendarNoteEntity(0, daysOfMonth[position]+"/"+monthYearText.toString().replace(' ', '/')))
//        println(position)`
//        task = taskViewModel.get
//        holder.bind()
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String?)
    }
}