package com.example.takenote.data.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.takenote.data.model.Note
import com.example.takenote.data.model.NotePriority
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDaoTest {

    // This is used to test asynchronous tasks
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TakeNoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TakeNoteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.notesDao()
    }

    @Test
    fun insertNoteItem() = runBlockingTest {
        val noteItem = Note(ID, title, description, url, date, mediumPriority)
        dao.insertNote(noteItem)

        val allNotes = dao.getAllData().getOrAwaitValue()

        assertThat(allNotes).contains(noteItem)
    }

    @Test
    fun deleteNoteItem() = runBlockingTest {
        val noteItem = Note(ID, title, description, url, date, mediumPriority)
        dao.insertNote(noteItem)
        dao.deleteNote(noteItem)

        val allNotes = dao.getAllData().getOrAwaitValue()

        assertThat(allNotes).doesNotContain(noteItem)
    }

    @Test
    fun deleteAllNotes() = runBlockingTest {
        val note1 = Note(ID, title, description, url, date, mediumPriority)
        val note2 = Note(ID2, title, description, url, date, mediumPriority)
        val note3 = Note(ID3, title, description, url, date, mediumPriority)

        dao.insertNote(note1)
        dao.insertNote(note2)
        dao.insertNote(note3)
        dao.deleteAllNotes()

        val allNotes = dao.getAllData().getOrAwaitValue()
        assertThat(allNotes).isEmpty()
    }

    @Test
    fun updateNoteItem() = runBlockingTest {
        val oldNote = Note(ID, title, description, url, date, lowPriority)
        val newNote = Note(ID, title, description, url, date, highPriority)

        dao.insertNote(oldNote)
        dao.updateNote(newNote)

        val allNotes = dao.getAllData().getOrAwaitValue()
        assertThat(allNotes[0].priority).isEqualTo(NotePriority.HIGH)
    }

    @Test
    fun sortNotesByHighPriority() = runBlockingTest {
        val note1 = Note(ID, title, description, url, date, lowPriority)
        val note2 = Note(ID2, title, description, url, date, mediumPriority)
        val note3 = Note(ID3, title, description, url, date, highPriority)

        dao.insertNote(note1)
        dao.insertNote(note2)
        dao.insertNote(note3)

        val allNotes = dao.getAllNotesSortedByHighPriority().getOrAwaitValue()
        assertThat(allNotes[0].priority).isEqualTo(highPriority)
        assertThat(allNotes[1].priority).isEqualTo(mediumPriority)
        assertThat(allNotes[2].priority).isEqualTo(lowPriority)
    }

    @Test
    fun sortNotesByLowPriority() = runBlockingTest {
        val note1 = Note(ID, title, description, url, date, highPriority)
        val note2 = Note(ID2, title, description, url, date, mediumPriority)
        val note3 = Note(ID3, title, description, url, date, lowPriority)

        dao.insertNote(note1)
        dao.insertNote(note2)
        dao.insertNote(note3)

        val allNotes = dao.getAllNotesSortedByLowPriority().getOrAwaitValue()
        assertThat(allNotes[0].priority).isEqualTo(lowPriority)
        assertThat(allNotes[1].priority).isEqualTo(mediumPriority)
        assertThat(allNotes[2].priority).isEqualTo(highPriority)
    }

    @Test
    fun searchDatabaseWithQuery() = runBlockingTest {
        val note1 = Note(ID, title, description, url, date, highPriority)
        val note2 = Note(ID2, title2, description, url, date, mediumPriority)
        val note3 = Note(ID3, title3, description, url, date, lowPriority)

        dao.insertNote(note1)
        dao.insertNote(note2)
        dao.insertNote(note3)

        val allNotes = dao.searchDatabase(title2).getOrAwaitValue()
        assertThat(allNotes.size).isEqualTo(1)
    }

    @After
    fun tearDown() {
        database.close()
    }
}
