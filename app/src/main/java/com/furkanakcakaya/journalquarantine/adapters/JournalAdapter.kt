package com.furkanakcakaya.journalquarantine.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.databinding.JournalItemBinding
import com.furkanakcakaya.journalquarantine.entities.JournalEntry

class JournalAdapter(
    private val mContext:Context,
    private val journalList: List<JournalEntry>
    ) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:JournalItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : JournalItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.journal_item,parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.journalItem = journalList[position]
    }

    override fun getItemCount(): Int {
        return journalList.size
    }
}
