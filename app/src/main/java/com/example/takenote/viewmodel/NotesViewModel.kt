package com.example.takenote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.takenote.data.model.Note
import com.example.takenote.data.persistence.TakeNoteDatabase
import com.example.takenote.data.repository.DataList
import com.example.takenote.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = TakeNoteDatabase.getDatabase(application).notesDao()
    private val repository: NoteRepository

    val getAllData: DataList
    val sortByHighPriority: DataList
    val sortByLowPriority: DataList

    init {
        repository = NoteRepository(noteDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortNotesByHighPriority
        sortByLowPriority = repository.sortNotesByLowPriority
    }

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDB(query: String): DataList {
        return repository.searchDB(query)
    }
}
