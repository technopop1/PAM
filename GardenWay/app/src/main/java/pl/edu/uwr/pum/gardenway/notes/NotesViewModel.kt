package pl.edu.uwr.pum.gardenway.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.edu.uwr.pum.gardenway.NoteEntity
import pl.edu.uwr.pum.gardenway.GardenRoomDatabase

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val db : GardenRoomDatabase
    val getAllPlants : LiveData<List<NoteEntity>>

    init {
        db = GardenRoomDatabase.getDatabase(application)
        getAllPlants = db.gardenDao().getNotes()
    }

    fun insert(noteEntity : NoteEntity) {
        viewModelScope.launch {
            db.gardenDao().insert(noteEntity)
        }
    }

    fun delete(id : Long) {
        viewModelScope.launch {
            db.gardenDao().delete(id)
        }
    }

    fun update(entity: NoteEntity) {
        viewModelScope.launch {
            db.gardenDao().update(entity)
        }
    }
}