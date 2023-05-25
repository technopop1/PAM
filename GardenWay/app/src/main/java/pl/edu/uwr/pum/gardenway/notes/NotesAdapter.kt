package pl.edu.uwr.pum.gardenway.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.pum.gardenway.NoteEntity
import pl.edu.uwr.pum.gardenway.R

class NotesAdapter(private val notesViewModel: NotesViewModel, notesComparator: NotesComparator) :
    ListAdapter<NoteEntity, NotesAdapter.TaskViewHolder>(notesComparator) {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = itemView.findViewById(R.id.note_title)
        private val date: TextView = itemView.findViewById(R.id.note_date)
        private val noteItem: ConstraintLayout = itemView.findViewById(R.id.note_item)

        fun bind(task: NoteEntity) {

            this.title.text = task.title
            this.date.text = task.creationDate

            noteItem.setOnClickListener {
                val bundle = bundleOf()
                bundle.putLong("ID_KEY", task.id)
                bundle.putString("NOTE_TITLE_KEY", task.title)
                bundle.putString("NOTE_DATE_CREATION_KEY", task.creationDate)
                bundle.putString("NOTE_DESCRIPTION_KEY", task.description)

                noteItem.findNavController()
                    .navigate(R.id.action_listOfNotesFragment_to_noteDetailFragment, bundle)
            }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount() =
        notesViewModel.getAllNotes.value?.toMutableList()?.size ?: mutableListOf<NoteEntity>().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val tasks = notesViewModel.getAllNotes.value?.get(position)!!
        holder.bind(tasks)
    }
}