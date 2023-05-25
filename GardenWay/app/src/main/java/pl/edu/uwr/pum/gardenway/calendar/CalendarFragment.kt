package pl.edu.uwr.pum.gardenway.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.pum.gardenway.NoteEntity
import pl.edu.uwr.pum.gardenway.R
import pl.edu.uwr.pum.gardenway.notes.NotesViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener {

    private var monthYearText: TextView? = null
    private var selectedDate: LocalDate? = null

    private val calendarViewModel: CalendarViewModel by viewModels()
    private val notesViewModel: NotesViewModel by viewModels()

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


    private fun initWidgets(view: View) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        monthYearText = view.findViewById(R.id.monthYearTV)
    }

    private fun setMonthView() {
        monthYearText?.text = selectedDate?.let { monthYearFromDate(it) }
        val daysInMonth = selectedDate?.let { daysInMonthArray(it) }

        calendarViewModel.getAllTimeData.observe(
            viewLifecycleOwner,
            Observer { calendarViewModel.getAllTimeData })
        adapter = daysInMonth?.let { CalendarAdapter(calendarViewModel, it, monthYearText, this) }!!
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = adapter

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
        val previousMonthActionButton: Button = view!!.findViewById(R.id.previousMonthActionButton)
        previousMonthActionButton.setOnClickListener {
            selectedDate = selectedDate?.minusMonths(1)
            setMonthView()
        }
    }

    private fun setNextMonthAction(view: View?) {
        val nextMonthActionButton: Button = view!!.findViewById(R.id.nextMonthActionButton)
        nextMonthActionButton.setOnClickListener {
            selectedDate = selectedDate?.plusMonths(1)
            setMonthView()
        }
    }

    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText != "") {
            val noteDate =
                "$dayText/" + selectedDate?.let { monthYearFromDate(it).replace(' ', '/') }

            var calendarNoteByDate: CalendarNoteEntity? = null
            Thread {
                calendarNoteByDate = calendarViewModel.getCalendarNoteByDate(noteDate)
            }.start()
            Thread.sleep(100)
            if (calendarNoteByDate == null) {
                val note = createNewNote(noteDate)
                createNewCalendarNote(noteDate)
                view?.let { goToNewTask(it, note) }
            } else {
                var note: NoteEntity? = null
                Thread {
                    note = notesViewModel.getNoteById(calendarNoteByDate!!.noteId)
                }.start()
                Thread.sleep(100)
                if (note != null)
                    view?.let { goToNewTask(it, note!!) }
            }

        }
    }

    private fun createNewNote(noteDate: String): NoteEntity {
        val noteEntity = NoteEntity(0, "Note #$noteDate", LocalDate.now().toString(), "<Opis>")
        notesViewModel.insert(noteEntity)
        notesViewModel.getAllNotes.observe(viewLifecycleOwner) { notesViewModel.getAllNotes }
        Thread.sleep(200)
        return noteEntity
    }

    private fun createNewCalendarNote(noteDate: String) {
        Thread {
            if (notesViewModel.getLastNote() != null) {
                calendarViewModel.insert(
                    CalendarNoteEntity(
                        0,
                        noteDate,
                        notesViewModel.getLastNote()?.id!!
                    )
                )
            }
        }.start()
        calendarViewModel.getAllTimeData.observe(viewLifecycleOwner) { calendarViewModel.getAllTimeData }
        notesViewModel.getAllNotes.observe(viewLifecycleOwner) { notesViewModel.getAllNotes }
        Thread.sleep(100)
    }

    private fun goToNewTask(view: View, task: NoteEntity) {
        val bundle = bundleOf()
        bundle.putLong("ID_KEY", task.id)
        bundle.putString("NOTE_TITLE_KEY", task.title)
        bundle.putString("NOTE_DATE_CREATION_KEY", task.creationDate)
        bundle.putString("NOTE_DESCRIPTION_KEY", task.description)
        bundle.putString("NOTE_CALENDAR_STATE", "calendar_route")

        view.findNavController()
            .navigate(R.id.action_calendarFragment_to_noteDetailFragment, bundle)
    }
}