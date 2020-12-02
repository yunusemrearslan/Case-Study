package com.example.takenote.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String,
    var imageURL: String?,
    var date: Long,
    var priority: NotePriority = NotePriority.MEDIUM,
    var isEdited: Boolean = false
) : Parcelable {
    val formattedDate: String
    get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)
    val isImageAvailable: Boolean
    get() = !imageURL.isNullOrEmpty()
}
