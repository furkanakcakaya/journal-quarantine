package com.furkanakcakaya.journalquarantine.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.adapters.JournalAdapter
import com.furkanakcakaya.journalquarantine.databinding.FragmentHomepageBinding
import com.furkanakcakaya.journalquarantine.utils.SwipeGesture
import com.furkanakcakaya.journalquarantine.viewmodels.HomepageViewModel
import com.google.android.material.snackbar.Snackbar


class HomepageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var viewModel: HomepageViewModel
    private val TAG = "HomepageFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tmp : HomepageViewModel by viewModels()
        viewModel = tmp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        binding.fragment = this

        viewModel.journalList.observe(viewLifecycleOwner) {
            createSwipeGesture(requireContext(),binding.rvEntries)
            binding.adapter = JournalAdapter(requireContext(),it, viewModel)
        }

        return binding.root
    }

    private fun createSwipeGesture(context: Context, recyclerView: RecyclerView){
        val swipeGesture = object : SwipeGesture(context){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                when(direction){
                    ItemTouchHelper.RIGHT -> {
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    ItemTouchHelper.LEFT -> {
                        val deletedJournalEntry = viewModel.journalList.value?.get(position)
                        if (deletedJournalEntry == null) {
                            Log.i(TAG, "onSwiped: deletedJournalEntry is null")
                            recyclerView.adapter?.notifyDataSetChanged()
                            return
                        }
                        viewModel.deleteJournalEntry(deletedJournalEntry.id)
                        Snackbar.make(recyclerView, "Misclicked? No worries", Snackbar.LENGTH_LONG).setAction("Undo") {
                            viewModel.insertJournalEntry(deletedJournalEntry)
                        }.show()
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }

}