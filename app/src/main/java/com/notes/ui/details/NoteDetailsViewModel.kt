package com.notes.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.data.NoteDatabase
import com.notes.data.NoteDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(
    private val noteDatabase: NoteDatabase
) : ViewModel() {

    fun onSaveNoteClick(title: String, content: String) {
        var modified: LocalDateTime = LocalDateTime.now()
        var createNote = NoteDbo(0,title, content, modified, modified)
        viewModelScope.launch(Dispatchers.IO) {
            noteDatabase.noteDao().insertAll(createNote)
        }

    }
}