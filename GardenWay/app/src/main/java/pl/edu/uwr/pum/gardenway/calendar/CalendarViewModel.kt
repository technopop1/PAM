package pl.edu.uwr.pum.gardenway.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.edu.uwr.pum.gardenway.GardenRoomDatabase


class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    private val db : GardenRoomDatabase
    val getAllTimeData : LiveData<List<CalendarNoteEntity>>

    init {
        db = GardenRoomDatabase.getDatabase(application)
        getAllTimeData = db.calendarNoteDao().getCalendarNotes()
    }

    fun insert(calendarNoteEntity : CalendarNoteEntity) {
        viewModelScope.launch {
            db.calendarNoteDao().insert(calendarNoteEntity)
        }
    }

    fun delete(id : Long) {
        viewModelScope.launch {
            db.calendarNoteDao().delete(id)
        }
    }

    fun update(entity: CalendarNoteEntity) {
        viewModelScope.launch {
            db.calendarNoteDao().update(entity)
        }
    }

    fun getCalendarNoteByDate(date : String) : CalendarNoteEntity? {
        return db.calendarNoteDao().getCalendarNoteByDate(date)
    }

    fun getCalendarNotes() : LiveData<List<CalendarNoteEntity>> {
        return db.calendarNoteDao().getCalendarNotes()
    }

    fun getLastElement() : CalendarNoteEntity? {
        return db.calendarNoteDao().getLastElement()
    }

    fun getAllElements() : List<CalendarNoteEntity> {
        return db.calendarNoteDao().getAllElements()
    }

}