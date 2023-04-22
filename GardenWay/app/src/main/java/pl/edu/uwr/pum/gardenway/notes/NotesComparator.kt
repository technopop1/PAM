package pl.edu.uwr.pum.gardenway.notes

import androidx.recyclerview.widget.DiffUtil
import pl.edu.uwr.pum.gardenway.NoteEntity

class NotesComparator : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem.id == newItem.id
    }
}