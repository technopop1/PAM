package pl.edu.uwr.pum.gardenway.calendar

import androidx.room.*
import pl.edu.uwr.pum.gardenway.NoteEntity

@Entity(
    tableName = "CALENDAR_NOTES", indices = [Index(value = ["NOTE_DATE"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class, parentColumns = ["ID"], childColumns = ["NOTE_ID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CalendarNoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "NOTE_DATE") val noteDate: String,  //String bo kotlin gowno ma problem z data pozdro
    @ColumnInfo(name = "NOTE_ID") val noteId: Long // Klucz obcy do tabeli NOTES

) {}
