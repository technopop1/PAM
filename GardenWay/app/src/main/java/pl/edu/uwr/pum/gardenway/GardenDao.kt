package pl.edu.uwr.pum.gardenway

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import pl.edu.uwr.pum.gardenway.calendar.CalendarNoteEntity

@Dao
interface PlantDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(plantEntity: PlantEntity)

    @Update
    suspend fun update(plantEntity: PlantEntity)

    @Query("DELETE FROM PLANTS WHERE ID= :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM PLANTS ORDER BY id")
    fun getPlants(): LiveData<List<PlantEntity>>

    @Query("SELECT * FROM PLANTS ORDER BY id DESC LIMIT 1")
    fun getLastElement(): PlantEntity

    @Query("SELECT * FROM PLANTS WHERE id = :id")
    fun getElementById(id: Long): PlantEntity

    @Query("SELECT COUNT(DISTINCT species) FROM PLANTS")
    fun getNumberOfSpecies(): Int

    @Query("SELECT SUM(cost) FROM PLANTS")
    fun getTotalKnownCost(): Float

    @Query("SELECT COUNT(id) FROM PLANTS")
    fun getNumberOfPlants(): Int
}

@Dao
interface NoteDaO {

    /// NOTES
    @Insert(onConflict = IGNORE)
    suspend fun insert(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("DELETE FROM NOTES WHERE ID= :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM NOTES ORDER BY id")
    fun getNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM NOTES ORDER BY id DESC LIMIT 1")
    fun getLastNote(): NoteEntity

    @Query("SELECT * FROM NOTES WHERE ID=:noteid")
    fun getNoteById(noteid: Long): NoteEntity

}

@Dao
interface CalendarNotesDaO {

    /// CALENDAR NOTES
    @Insert(onConflict = IGNORE)
    suspend fun insert(noteEntity: CalendarNoteEntity)

    @Update
    suspend fun update(noteEntity: CalendarNoteEntity)

    @Query("DELETE FROM CALENDAR_NOTES WHERE ID= :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM CALENDAR_NOTES ORDER BY ID")
    fun getCalendarNotes(): LiveData<List<CalendarNoteEntity>>

    // w założeniu 1 notatka na dzień z kalendarza
    @Query("SELECT * FROM CALENDAR_NOTES WHERE NOTE_DATE=:noteDate")
    fun getCalendarNoteByDate(noteDate: String): CalendarNoteEntity?

    @Query("SELECT * FROM CALENDAR_NOTES ORDER BY ID DESC LIMIT 1")
    fun getLastElement(): CalendarNoteEntity?

    @Query("SELECT * FROM CALENDAR_NOTES ORDER BY ID")
    fun getAllElements(): List<CalendarNoteEntity>
}