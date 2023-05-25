package pl.edu.uwr.pum.gardenway

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "PLANTS"/*, foreignKeys = [ForeignKey(
    entity = SectorEntity::class,
    childColumns = ["ID"],
    parentColumns = ["IDs"]
)]*/
)
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "PLANT_NAME") val title: String,
    @ColumnInfo(name = "PLANT_DATE") val plantDate: String,
    @ColumnInfo(name = "LAST_WATERING_DATE") val lastWateringDate: String,
    @ColumnInfo(name = "WATERING_INTERVALS") val wateringInterval: Int,
    @ColumnInfo(name = "SPECIES") val species: String,
    @ColumnInfo(name = "SECTOR") val sector: String,
    @ColumnInfo(name = "COST") val cost: Float,
    @ColumnInfo(name = "DESCRIPTION") val description: String,
    @ColumnInfo(name = "WATER_DEMAND") val waterDemand: Boolean = false
) {
}

@Entity(tableName = "NOTES")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val id: Long,
    @ColumnInfo(name = "NOTE_TITLE") val title: String,
    @ColumnInfo(name = "NOTE_DATE_CREATION") val creationDate: String,
    @ColumnInfo(name = "NOTE_DESCRIPTION") val description: String,
) {}
