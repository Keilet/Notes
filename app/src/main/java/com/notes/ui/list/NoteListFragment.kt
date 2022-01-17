package com.notes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.notes.databinding.FragmentNoteListBinding
import com.notes.databinding.ListItemNoteBinding
import com.notes.di.DependencyManager
import com.notes.ui._base.FragmentNavigator
import com.notes.ui._base.ViewBindingFragment
import com.notes.ui._base.findImplementationOrThrow
import com.notes.ui.details.NoteDetailsFragment
import java.time.LocalDateTime

class NoteListFragment : ViewBindingFragment<FragmentNoteListBinding>(
    FragmentNoteListBinding::inflate
) {

    private val viewModel by lazy { DependencyManager.noteListViewModel() }

    override fun onViewBindingCreated(
        viewBinding: FragmentNoteListBinding,
        savedInstanceState: Bundle?
    ) {
        super.onViewBindingCreated(viewBinding, savedInstanceState)

        val noteListClickListener: RecyclerViewAdapter.OnClickListener =
            object : RecyclerViewAdapter.OnClickListener {
                override fun onClick(note: NoteListItem, position: Int) {
                    viewModel.navigateToNoteCreation.observe(
                        viewLifecycleOwner,
                        {
                            findImplementationOrThrow<FragmentNavigator>()
                                .navigateTo(
                                    NoteDetailsFragment(note)
                                )

                        }
                    )
                    viewModel.onCreateNoteClick()
                }
            }

        val recyclerViewAdapter = RecyclerViewAdapter(noteListClickListener)

        viewBinding.list.adapter = recyclerViewAdapter
        viewBinding.list.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayout.VERTICAL
            )
        )
        viewBinding.createNoteButton.setOnClickListener {
            viewModel.onCreateNoteClick()
        }

/*        viewModel.notes.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    recyclerViewAdapter.setItems(it.sortedByDescending { it.modifiedAt })
                }
            }
        )*/

        viewModel.notes.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    recyclerViewAdapter.refreshNotes(it.sortedByDescending { it.modifiedAt })
                }
            }
        )

        viewModel.navigateToNoteCreation.observe(
            viewLifecycleOwner,
            {
                findImplementationOrThrow<FragmentNavigator>()
                    .navigateTo(
                        NoteDetailsFragment(
                            NoteListItem(
                                0,
                                "",
                                "",
                                LocalDateTime.now(),
                                LocalDateTime.now()
                            )
                        )
                    )

            }
        )
    }

    private class RecyclerViewAdapter(onClickListener: OnClickListener) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        private var items = mutableListOf<NoteListItem>()

        interface OnClickListener {
            fun onClick(note: NoteListItem, position: Int)
        }

        var thisClickListener: OnClickListener = onClickListener

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ) = ViewHolder(
            ListItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            var note: NoteListItem = items[position]
            holder.bind(items[position])
            holder.itemView.setOnClickListener(View.OnClickListener {
                thisClickListener.onClick(note, position)
            })
        }

        override fun getItemCount() = items.size

/*        fun setItems(
            items: List<NoteListItem>
        ) {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }*/

        fun refreshNotes(item: List<NoteListItem>) {
            this.items = item as MutableList<NoteListItem>
            notifyDataSetChanged()
        }


        private class ViewHolder(
            private val binding: ListItemNoteBinding
        ) : RecyclerView.ViewHolder(
            binding.root
        ) {

            fun bind(
                note: NoteListItem
            ) {
                binding.titleLabel.text = note.title
                binding.contentLabel.text = note.content
            }

        }

    }

}