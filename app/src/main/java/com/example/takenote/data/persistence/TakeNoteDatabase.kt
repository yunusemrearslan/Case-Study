package com.example.takenote.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.takenote.data.model.Note

/**
 * Application Database Manager, manages all of the DB operations.
 *
 * @see RoomDatabase
 *
 */
@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TakeNoteDatabase : RoomDatabase() {

    abstract fun notesDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: TakeNoteDatabase? = null

        fun getDatabase(context: Context): TakeNoteDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TakeNoteDatabase::class.java, "note_database"
            ).build()
    }
}
