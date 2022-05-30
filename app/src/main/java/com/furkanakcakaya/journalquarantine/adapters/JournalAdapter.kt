package com.furkanakcakaya.journalquarantine.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.databinding.JournalItemBinding
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.fragments.HomepageFragmentDirections

class JournalAdapter(
    private val mContext:Context,
    private val journalList: List<JournalEntry>,
    private val viewModel: ViewModel
    ) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:JournalItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : JournalItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.journal_item,parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.journalItem = journalList[position]
        holder.binding.lottieItem.setAnimation("${journalList[position].mood}.json")

        holder.binding.cvJournal.setOnClickListener {
            val nav = HomepageFragmentDirections.actionHomepageFragmentToUpdateEntryFragment(journalList[position])
            Navigation.findNavController(it).navigate(nav)
        }
    }

    override fun getItemCount(): Int {
        return journalList.size
    }
}
