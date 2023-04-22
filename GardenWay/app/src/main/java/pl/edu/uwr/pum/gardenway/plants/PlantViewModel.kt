package pl.edu.uwr.pum.gardenway.plants

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.edu.uwr.pum.gardenway.GardenRoomDatabase
import pl.edu.uwr.pum.gardenway.NoteEntity
import pl.edu.uwr.pum.gardenway.PlantEntity

class PlantViewModel(application: Application) : AndroidViewModel(application) {
    private val db : GardenRoomDatabase
    val getAllPlants : LiveData<List<PlantEntity>>

    init {
        db = GardenRoomDatabase.getDatabase(application)
        getAllPlants = db.plantDao().getPlants()
    }

    fun insert(plantEntity : PlantEntity) {
        viewModelScope.launch {
            db.plantDao().insert(plantEntity)
        }
    }

    fun delete(id : Long) {
        viewModelScope.launch {
            db.plantDao().delete(id)
        }
    }

    fun update(entity: PlantEntity) {
        viewModelScope.launch {
            db.plantDao().update(entity)
        }
    }

    fun getNumberOfPlants() : Int {
        return db.plantDao().getNumberOfPlants()
    }

    fun getNumberOfSpecies() : Int {
        return db.plantDao().getNumberOfSpecies()
    }

    fun getTotalKnownCost() : Float{
        return db.plantDao().getTotalKnownCost()
    }
}