package com.example.takenote.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.takenote.R
import com.example.takenote.data.model.Note
import com.example.takenote.viewmodel.NotesViewModel
import com.example.takenote.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_newnote.*
import kotlinx.android.synthetic.main.fragment_newnote.view.*

class NewNoteFragment : Fragment() {

    private val mNoteViewModel: NotesViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_newnote, container, false)

        setHasOptionsMenu(true)

        //Spinner Item Selected Listener
        view.priorities_spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_note_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDatabase() {
        val title = title_text.text.toString()
        val priority = priorities_spinner.selectedItem.toString()
        val description = description_text.text.toString()
        val imageURL = image_url_text.text.toString()

        val validation = mSharedViewModel.verifyData(title, description)
        if (validation) {
            //If data is valid, insert it to db
            val newNote = Note(
                0,
                title,
                description,
                imageURL,
                System.currentTimeMillis(),
                mSharedViewModel.parsePriority(priority)
            )
            mNoteViewModel.insertNote(newNote)
            showSnackBarMessage(getString(R.string.successfully_added))
            //back to list fragment
            findNavController().navigate(R.id.action_newNoteFragment_to_notesFragment)
        } else {
            showSnackBarMessage(getString(R.string.please_fill_all_the_required_fields))
        }
    }

    private fun showSnackBarMessage(message:String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}