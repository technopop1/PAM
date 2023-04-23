package pl.edu.uwr.pum.gardenway.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.edu.uwr.pum.gardenway.GardenRoomDatabase
import pl.edu.uwr.pum.gardenway.NoteEntity

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val db : GardenRoomDatabase
    val getAllPlants : LiveData<List<NoteEntity>>

    init {
        db = GardenRoomDatabase.getDatabase(application)
        getAllPlants = db.noteDao().getNotes()
    }

    fun insert(noteEntity : NoteEntity) {
        viewModelScope.launch {
            db.noteDao().insert(noteEntity)
        }
    }

    fun delete(id : Long) {
        viewModelScope.launch {
            db.noteDao().delete(id)
        }
    }

    fun update(entity: NoteEntity) {
        viewModelScope.launch {
            db.noteDao().update(entity)
        }
    }
}