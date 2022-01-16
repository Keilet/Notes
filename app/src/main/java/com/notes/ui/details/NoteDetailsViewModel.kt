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

    fun onSaveNoteClick(id: Long, title: String, content: String) {
        var modified: LocalDateTime = LocalDateTime.now()
        if (id>0){
            viewModelScope.launch(Dispatchers.IO) {
                noteDatabase.noteDao().update(id,title,content,modified)
            }
        } else {
            var createNote = NoteDbo(id, title, content, modified, modified)
            viewModelScope.launch(Dispatchers.IO) {
                noteDatabase.noteDao().insertAll(createNote)
            }
        }
    }

    fun onDeleteNoteClick(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDatabase.noteDao().delete(id)
        }
    }
}