package pl.edu.uwr.pum.gardenway

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface PlantDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(plantEntity: PlantEntity)

    @Update
    suspend fun update(plantEntity: PlantEntity)

    @Query("DELETE FROM plant_table WHERE ID= :id")
    suspend fun delete(id : Long)

    @Query("SELECT * FROM plant_table ORDER BY id")
    fun getPlants() : LiveData<List<PlantEntity>>

    @Query("SELECT * FROM plant_table ORDER BY id DESC LIMIT 1")
    fun getLastElement() : PlantEntity

    @Query("SELECT * FROM plant_table WHERE id = :id")
    fun getElementById(id : Long) : PlantEntity

    @Query("SELECT COUNT(DISTINCT species) FROM plant_table")
    fun getNumberOfSpecies() : Int

    @Query("SELECT SUM(cost) FROM plant_table")
    fun getTotalKnownCost() : Float

    @Query("SELECT COUNT(id) FROM plant_table")
    fun getNumberOfPlants() : Int
}

@Dao
interface GarderDaO {

    /// NOTES
    @Insert(onConflict = IGNORE)
    suspend fun insert(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("DELETE FROM garden_calendar WHERE ID= :id")
    suspend fun delete(id : Long)

    @Query("SELECT * FROM garden_calendar ORDER BY id")
    fun getNotes() : LiveData<List<NoteEntity>>

}