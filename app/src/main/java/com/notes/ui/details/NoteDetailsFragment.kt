package com.notes.ui.details

import android.os.Bundle
import com.notes.databinding.FragmentNoteDetailsBinding
import com.notes.ui.RootActivity
import com.notes.ui._base.ViewBindingFragment

class NoteDetailsFragment : ViewBindingFragment<FragmentNoteDetailsBinding>(
    FragmentNoteDetailsBinding::inflate
) {
    override fun onViewBindingCreated(
        viewBinding: FragmentNoteDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)

        viewBinding.toolbar.setNavigationOnClickListener {
            (activity as RootActivity).onBackPressed()
        }

    }
}