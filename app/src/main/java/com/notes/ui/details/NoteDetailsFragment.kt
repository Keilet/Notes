package com.notes.ui.details

import android.os.Bundle
import android.widget.Toast
import com.notes.databinding.FragmentNoteDetailsBinding
import com.notes.di.DependencyManager
import com.notes.ui.RootActivity
import com.notes.ui._base.ViewBindingFragment

class NoteDetailsFragment : ViewBindingFragment<FragmentNoteDetailsBinding>(
    FragmentNoteDetailsBinding::inflate
) {
    private val viewModel by lazy { DependencyManager.noteDetailsViewModel() }
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