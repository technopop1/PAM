package pl.edu.uwr.pum.gardenway.calendar

import androidx.recyclerview.widget.DiffUtil

class CalendarComparator : DiffUtil.ItemCallback<CalendarNoteEntity>() {
    override fun areItemsTheSame(
        oldItem: CalendarNoteEntity,
        newItem: CalendarNoteEntity
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: CalendarNoteEntity,
        newItem: CalendarNoteEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }
}