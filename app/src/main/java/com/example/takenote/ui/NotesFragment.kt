package com.example.takenote.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.takenote.R
import com.example.takenote.adapters.NotesAdapter
import com.example.takenote.databinding.FragmentNotesBinding
import com.example.takenote.data.model.Note
import com.example.takenote.util.SwipeToDelete
import com.example.takenote.viewmodel.NotesViewModel
import com.example.takenote.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class NotesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val mNotesViewModel: NotesViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val adapter: NotesAdapter by lazy { NotesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Data binding
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        //setup recyclerview
        setupRecyclerView()

        //Observe LiveData
        mNotesViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //Set Menu
        setHasOptionsMenu(true)

        requireActivity().actionBar?.title = "Take Note!"


        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAll()
            R.id.menu_priority_high -> mNotesViewModel.sortByHighPriority.observe(this, Observer { adapter.setData(it) })
            R.id.menu_priority_low -> mNotesViewModel.sortByLowPriority.observe(this, Observer { adapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreDeletedData(view: View, deletedItem: Note){
        val snackBar = Snackbar.make(
            view,
            "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(getString(R.string.undo)) {
            mNotesViewModel.insertNote(deletedItem)
        }
        snackBar.show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchDatabase(newText)
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        mNotesViewModel.searchDB(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewNotes
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object: SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.noteList[viewHolder.adapterPosition]
                //delete item
                mNotesViewModel.deleteNote(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                //Restore deleted item
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun deleteAll(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){ _, _ ->
            mNotesViewModel.deleteAll()
            showSnackBarMessage(getString(R.string.all_notes_removed_successfully))
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
            builder.setTitle(getString(R.string.delete_everything))
            builder.setMessage(getString(R.string.are_you_sure_you_want_to_remove_everything))
            builder.create().show()
    }

    private fun showSnackBarMessage(message: String){
        Snackbar.make(requireView(),message, Snackbar.LENGTH_SHORT).show()
    }
}