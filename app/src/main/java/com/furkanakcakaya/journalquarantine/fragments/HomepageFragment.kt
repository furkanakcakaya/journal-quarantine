package com.furkanakcakaya.journalquarantine.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.search.clearFocus()
                if (p0 != null) {
                    viewModel.searchJournal(p0)
                }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.searchJournal(p0)
                }
                return true
            }
        })
        return binding.root
    }

    private fun createSwipeGesture(context: Context, recyclerView: RecyclerView){
        val swipeGesture = object : SwipeGesture(context){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val currentEntry = viewModel.journalList.value?.get(position)
                when(direction){
                    ItemTouchHelper.RIGHT -> {
                        if (currentEntry == null) {
                            recyclerView.adapter?.notifyDataSetChanged()
                            return
                        }
                        val nav = HomepageFragmentDirections.actionHomepageFragmentToUpdateEntryFragment(currentEntry)
                        Navigation.findNavController(binding.root).navigate(nav)
                    }
                    ItemTouchHelper.LEFT -> {
                        if (currentEntry == null) {
                            recyclerView.adapter?.notifyDataSetChanged()
                            return
                        }
                        viewModel.deleteJournalEntry(currentEntry.id)
                        Snackbar.make(recyclerView, "Misclicked? No worries", Snackbar.LENGTH_LONG).setAction("Undo") {
                            viewModel.insertJournalEntry(currentEntry)
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