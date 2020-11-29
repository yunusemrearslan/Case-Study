package com.example.takenote.util

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.takenote.R
import com.example.takenote.data.model.Note
import com.example.takenote.data.model.NotePriority
import com.example.takenote.ui.NotesFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object{

        @BindingAdapter("android:navigateToNewNoteFragment")
        @JvmStatic
        fun navigateToNewNoteFragment(view: FloatingActionButton, navigate:Boolean){
            view.setOnClickListener {
                if (navigate){
                    view.findNavController().navigate(R.id.action_notesFragment_to_newNoteFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:isEdited")
        @JvmStatic
        fun isEdited(view: View, isEdited: Boolean){
            if (isEdited)
                view.visibility = View.VISIBLE
            else
                view.visibility = View.INVISIBLE
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: NotePriority){
            when(priority){
                NotePriority.HIGH -> { view.setSelection(0) }
                NotePriority.MEDIUM -> { view.setSelection(1) }
                NotePriority.LOW -> { view.setSelection(2) }
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: NotePriority){
            when(priority){
                NotePriority.HIGH -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red)) }
                NotePriority.MEDIUM -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow)) }
                NotePriority.LOW -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green)) }
            }
        }

        @BindingAdapter("android:sendNoteToEditFragment")
        @JvmStatic
        fun sendNoteToEditFragment(view: ConstraintLayout, currentItem: Note){
            view.setOnClickListener {
                val action = NotesFragmentDirections.actionNotesFragmentToEditNoteFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }

    }
}