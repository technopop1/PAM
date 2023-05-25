package pl.edu.uwr.pum.gardenway.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.edu.uwr.pum.gardenway.NoteEntity
import pl.edu.uwr.pum.gardenway.R

class NoteDetailFragment : Fragment() {
    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_note_detail, container, false)
        val noteTitle: EditText = view.findViewById(R.id.note_title)
        val noteCreationDate: TextView = view.findViewById(R.id.note_date)
        val noteDescription: EditText = view.findViewById(R.id.note_description)

        noteTitle.setText(arguments?.getString("NOTE_TITLE_KEY"))
        noteCreationDate.text = arguments?.getString("NOTE_DATE_CREATION_KEY")
        noteDescription.setText(arguments?.getString("NOTE_DESCRIPTION_KEY"))

        backToListOfPlants(view, noteTitle, noteCreationDate, noteDescription)
        setDeleteButtonListener(view)

        return view
    }

    private fun setDeleteButtonListener(view: View) {
        val deleteLayout: ConstraintLayout = view.findViewById(R.id.deleteLayout)
        deleteLayout.setOnClickListener {
            notesViewModel.delete(arguments?.getLong("ID_KEY")!!)
            notesViewModel.getAllNotes.observe(
                viewLifecycleOwner,
                Observer { notesViewModel.getAllNotes })
            if (arguments?.get("NOTE_CALENDAR_STATE")?.equals("calendar_route") == true) {
                Navigation.findNavController(view)
                    .navigate(NoteDetailFragmentDirections.actionNoteDetailFragmentToCalendarFragment())
            } else {
                Navigation.findNavController(view)
                    .navigate(NoteDetailFragmentDirections.actionNoteDetailFragmentToListOfNotesFragment())
            }
        }
    }

    // back and update element from edit texts
    private fun backToListOfPlants(
        view: View,
        noteTitle: EditText,
        noteCreationDate: TextView,
        noteDescription: TextView
    ) {
        view.findViewById<FloatingActionButton>(R.id.fab_to_fragmentListOfPlants)
            .setOnClickListener {
                Thread {
                    notesViewModel.update(
                        NoteEntity(
                            arguments?.getLong("ID_KEY")!!,
                            noteTitle.text.toString(),
                            noteCreationDate.text.toString(),
                            noteDescription.text.toString()
                        )
                    )
                }.start()
                if (arguments?.get("NOTE_CALENDAR_STATE")?.equals("calendar_route") == true) {
                    Navigation.findNavController(view)
                        .navigate(NoteDetailFragmentDirections.actionNoteDetailFragmentToCalendarFragment())
                } else {
                    Navigation.findNavController(view)
                        .navigate(NoteDetailFragmentDirections.actionNoteDetailFragmentToListOfNotesFragment())
                }
            }
    }

}
