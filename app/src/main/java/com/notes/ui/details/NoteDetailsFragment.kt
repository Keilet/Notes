package com.notes.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.notes.databinding.FragmentNoteDetailsBinding
import com.notes.di.DependencyManager
import com.notes.ui.RootActivity
import com.notes.ui._base.ViewBindingFragment
import com.notes.ui.list.NoteListItem

class NoteDetailsFragment(note: NoteListItem) : ViewBindingFragment<FragmentNoteDetailsBinding>(
    FragmentNoteDetailsBinding::inflate
) {
    private val viewModel by lazy { DependencyManager.noteDetailsViewModel() }

    val note = note

    override fun onViewBindingCreated(
        viewBinding: FragmentNoteDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)

        viewBinding.toolbar.setNavigationOnClickListener {
            (activity as RootActivity).onBackPressed()
        }
        var detailsTitle = viewBinding.detailsTitleLabel
        var detailsContent = viewBinding.detailsContentLabel
        var detailsId=note.id

        if (detailsId > 0) {
            detailsTitle.setText(note.title)
            detailsContent.setText(note.content)
            viewBinding.toolbar.title = note.title
        } else {
            detailsTitle.text.clear()
            detailsContent.text.clear()
        }

        viewBinding.saveButton.setOnClickListener {
            if (detailsTitle.text.isNullOrEmpty()) {
                detailsTitle.setError("Введите название заметки")
                detailsTitle.requestFocus()
            }
            if (detailsContent.text.isNullOrEmpty()) {
                detailsContent.setError("Введите текст заметки")
                detailsContent.requestFocus()
            }
            if (detailsTitle.text.isNullOrEmpty() || detailsContent.text.isNullOrEmpty()) {

            } else {
                viewModel.onSaveNoteClick(
                    detailsTitle.text.toString(),
                    detailsContent.text.toString(),
                )
                Toast.makeText(context, "Заметка сохранена", Toast.LENGTH_LONG).show()
            }
        }
    }
}