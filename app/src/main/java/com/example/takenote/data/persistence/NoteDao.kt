package com.example.takenote.data.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.takenote.data.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE title LIKE:query")
    fun searchDatabase(query: String): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun getAllNotesSortedByHighPriority() : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun getAllNotesSortedByLowPriority() : LiveData<List<Note>>

}