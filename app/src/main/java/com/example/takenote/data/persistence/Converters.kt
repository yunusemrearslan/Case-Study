package com.example.takenote.data.persistence

import androidx.room.TypeConverter
import com.example.takenote.data.model.NotePriority

class Converters {

    @TypeConverter
    fun fromPriority(priority: NotePriority) = priority.name

    @TypeConverter
    fun toPriority(priority: String) = NotePriority.valueOf(priority)
}