package com.furkanakcakaya.journalquarantine.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository
import com.furkanakcakaya.journalquarantine.utils.SwipeGesture
import com.google.android.material.snackbar.Snackbar

class HomepageViewModel : ViewModel(){
    private val TAG = "HomepageViewModel"
    private val jRepo = JournalRepository()
    var journalList: LiveData<List<JournalEntry>> = jRepo.getJournalData()

    fun createSwipeGesture(context: Context, recyclerView: RecyclerView){
        val swipeGesture = object : SwipeGesture(context){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                when(direction){
                    ItemTouchHelper.RIGHT -> {

                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    ItemTouchHelper.LEFT -> {
                        Log.i(TAG, "onSwiped: left delete")
                        val deletedJournalEntry = journalList.value?.get(position)
                        if (deletedJournalEntry == null) {
                            Log.i(TAG, "onSwiped: deletedJournalEntry is null")
                            recyclerView.adapter?.notifyDataSetChanged()
                            return
                        }
                        jRepo.deleteJournalEntry(deletedJournalEntry.id)
                        Snackbar.make(recyclerView, "Misclicked? No worries", Snackbar.LENGTH_LONG).setAction("Undo") {
                            jRepo.insertJournalEntry(deletedJournalEntry)
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