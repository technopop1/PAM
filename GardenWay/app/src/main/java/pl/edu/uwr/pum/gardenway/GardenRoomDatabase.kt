package pl.edu.uwr.pum.gardenway

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.edu.uwr.pum.gardenway.calendar.CalendarNoteEntity

@Database(
    entities = [PlantEntity::class, NoteEntity::class, CalendarNoteEntity::class/*, SectorEntity::class*/],
    version = 1,
    exportSchema = false
)
abstract class GardenRoomDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    abstract fun noteDao(): NoteDaO

    abstract fun calendarNoteDao(): CalendarNotesDaO

    companion object {
        @Volatile
        private var INSTANCE: GardenRoomDatabase? = null

        fun getDatabase(application: Application): GardenRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    GardenRoomDatabase::class.java,
                    "plant_database_kotlin"
                ).build().also { INSTANCE = it }
                instance
            }
        }
    }
}