package com.example.takenote.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.takenote.R
import com.example.takenote.data.model.Note
import com.example.takenote.databinding.FragmentEditnoteBinding
import com.example.takenote.viewmodel.NotesViewModel
import com.example.takenote.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar

class EditNoteFragment : Fragment() {

    private val args by navArgs<EditNoteFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mNoteViewModel: NotesViewModel by viewModels()

    private var _binding: FragmentEditnoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //data binding
        _binding = FragmentEditnoteBinding.inflate(inflater, container, false)
        binding.args = args

        //set menu
        setHasOptionsMenu(true)

        //Spinner Item Selected Listener
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_note_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateNote()
            R.id.menu_delete -> deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateNote() {
        val title = binding.currentTitleText.text.toString()
        val description = binding.currentDescriptionText.text.toString()
        val priority = binding.currentPrioritiesSpinner.selectedItem.toString()
        val imageURL = binding.currentImageUrlText.text.toString()

        val validation = mSharedViewModel.verifyData(title, description)
        if (validation) {
            //update current note
            val updatedNote = Note(
                args.currentItem.id,
                title,
                description,
                mSharedViewModel.validateImageUrl(imageURL),
                args.currentItem.date,
                mSharedViewModel.parsePriority(priority),
                true
            )
            mNoteViewModel.updateNote(updatedNote)
            showSnackBarMessage(getString(R.string.successfully_updated))
            //back to list fragment
            findNavController().navigate(R.id.action_editNoteFragment_to_notesFragment)
        } else {
            showSnackBarMessage(getString(R.string.please_fill_all_required_fields))
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mNoteViewModel.deleteNote(args.currentItem)
            showSnackBarMessage("Successfully removed: ${args.currentItem.title}")
            //back to list fragment
            findNavController().navigate(R.id.action_editNoteFragment_to_notesFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
        builder.create().show()
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}