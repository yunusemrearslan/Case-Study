package com.example.takenote.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.takenote.data.model.Note

class NoteDiffUtil(
    private val oldList: List<Note>,
    private val newList: List<Note>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].description == newList[newItemPosition].description &&
                oldList[oldItemPosition].priority == newList[newItemPosition].priority &&
                oldList[oldItemPosition].date == newList[newItemPosition].date &&
                oldList[oldItemPosition].imageURL == newList[newItemPosition].imageURL
    }
}
