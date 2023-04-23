package pl.edu.uwr.pum.gardenway.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.pum.gardenway.R
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener {

    private var monthYearText: TextView? = null
    private var selectedDate: LocalDate? = null

    private val taskViewModel : CalendarViewModel by viewModels()

    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var adapter: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calendar_board, container, false)

        initWidgets(view)
        selectedDate = LocalDate.now()
        setMonthView()
        setPreviousMonthAction(view)
        setNextMonthAction(view)

        return view
    }


    private fun initWidgets(view : View) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText = view.findViewById(R.id.monthYearTV)
    }

    private fun setMonthView() {
        monthYearText?.text = selectedDate?.let { monthYearFromDate(it) }
        val daysInMonth = selectedDate?.let { daysInMonthArray(it) }
//        adapter = daysInMonth?.let { CalendarAdapter(taskViewModel, it, monthYearText, this, CalendarComparator()) }!!

        taskViewModel.getAllTimeData.observe(viewLifecycleOwner, Observer { taskViewModel.getAllTimeData })
        taskViewModel.getAllTimeData.value?.forEach {
            println(it.noteDate)
        }
        adapter = daysInMonth?.let { CalendarAdapter(taskViewModel, it, monthYearText, this) }!!
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = adapter

//        taskViewModel.getAllTimeData.observe(viewLifecycleOwner) {
//                tasks -> tasks?.let { adapter.submitList(it) }
//        }
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth: LocalDate = selectedDate?.withDayOfMonth(1)!!
        val dayOfWeek: Int = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    private fun setPreviousMonthAction(view: View?) {
        val previousMonthActionButton : Button = view!!.findViewById(R.id.previousMonthActionButton)
        previousMonthActionButton.setOnClickListener {
            selectedDate = selectedDate?.minusMonths(1)
            setMonthView()
        }
    }

    private fun setNextMonthAction(view: View?) {
        val nextMonthActionButton : Button = view!!.findViewById(R.id.nextMonthActionButton)
        nextMonthActionButton.setOnClickListener {
            selectedDate = selectedDate?.plusMonths(1)
            setMonthView()
        }
    }

    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            val noteDate = "$dayText/" + selectedDate?.let {monthYearFromDate(it).replace(' ', '/')}

            taskViewModel.getAllTimeData.observe(viewLifecycleOwner, Observer { taskViewModel.getAllTimeData })
            taskViewModel.insert(CalendarNoteEntity(0,noteDate))
            Thread {
                println(taskViewModel.getLastElement())
            }.start()

            val message = "Selected Date $noteDate"
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}