package pl.edu.uwr.pum.gardenway.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.edu.uwr.pum.gardenway.NoteEntity
import pl.edu.uwr.pum.gardenway.R
import java.time.LocalDate

class ListOfNotesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter

    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        recyclerView = view.findViewById(R.id.notes_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = NotesAdapter(notesViewModel, NotesComparator())
        notesViewModel.getAllNotes.observe(viewLifecycleOwner) { tasks ->
            tasks?.let { adapter.submitList(it) }
        }

        setFabOnClick(view)
        recyclerView.adapter = adapter

        return view
    }

    private fun setFabOnClick(view: View) {
        val fab = view.findViewById<FloatingActionButton>(R.id.note_fab)
        fab.setOnClickListener {
            var num = notesViewModel.getAllNotes.value?.maxByOrNull { it.id }?.id
            num = if (num != null) num + 1 else 0

            val task = NoteEntity(num, "Note #$num", LocalDate.now().toString(), "<Opis>")

            notesViewModel.insert(task)
            notesViewModel.getAllNotes.observe(viewLifecycleOwner) { tasks ->
                tasks?.let { adapter.submitList(it) }
            }

            goToNewTask(view, task)
        }
    }

    private fun goToNewTask(view: View, task: NoteEntity) {
        val bundle = bundleOf()
        bundle.putLong("ID_KEY", task.id)
        bundle.putString("NOTE_TITLE_KEY", task.title)
        bundle.putString("NOTE_DATE_CREATION_KEY", task.creationDate)
        bundle.putString("NOTE_DESCRIPTION_KEY", task.description)

        view.findNavController()
            .navigate(R.id.action_listOfNotesFragment_to_noteDetailFragment, bundle)
    }
}
