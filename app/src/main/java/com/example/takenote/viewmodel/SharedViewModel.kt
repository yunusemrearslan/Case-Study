package com.example.takenote.viewmodel

import android.app.Application
import android.util.Patterns
import android.view.View
import android.webkit.URLUtil
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.takenote.R
import com.example.takenote.data.model.Note
import com.example.takenote.data.model.NotePriority

/**
 * This class is used by multiple fragments for the same purposes.
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(noteList: List<Note>) {
        emptyDatabase.value = noteList.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.yellow
                        )
                    )
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.green
                        )
                    )
                }
            }
        }
    }

    fun verifyData(title: String, description: String): Boolean {
        return !(title.isEmpty() || description.isEmpty())
    }

    fun validateImageUrl(url: String): String {
        return if (URLUtil.isValidUrl(url) && Patterns.WEB_URL.matcher(url).matches())
            url
        else
            return ""
    }

    fun parsePriority(priority: String): NotePriority {
        return when (priority) {
            "High Priority" -> {
                NotePriority.HIGH
            }
            "Medium Priority" -> {
                NotePriority.MEDIUM
            }
            "Low Priority" -> {
                NotePriority.LOW
            }

            else -> NotePriority.LOW
        }
    }
}
