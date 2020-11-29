package com.example.takenote.data.repository

import androidx.lifecycle.LiveData
import com.example.takenote.data.model.Note
import com.example.takenote.data.persistence.NoteDao

typealias DataList =  LiveData<List<Note>>

class NoteRepository(private val noteDao: NoteDao) {

    val getAllData : DataList = noteDao.getAllData()
    val sortNotesByHighPriority: DataList = noteDao.getAllNotesSortedByHighPriority()
    val sortNotesByLowPriority: DataList = noteDao.getAllNotesSortedByLowPriority()

    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun deleteAll(){
        noteDao.deleteAllNotes()
    }

    fun searchDB(query: String): DataList {
        return noteDao.searchDatabase(query)
    }
}