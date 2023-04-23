package pl.edu.uwr.pum.gardenway.calendar

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "CALENDAR_NOTES", indices = [Index(value = ["NOTE_DATE"], unique = true)])
data class CalendarNoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "NOTE_DATE") val noteDate: String
    //String bo kotlin gowno ma problem z data pozdro
) {}
