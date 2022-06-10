package com.furkanakcakaya.journalquarantine.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.databinding.FragmentViewEntryBinding

class ViewEntryFragment : Fragment() {
    private lateinit var binding: FragmentViewEntryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_entry, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val entry =  ViewEntryFragmentArgs.fromBundle(requireArguments()).entry

        binding.entry = entry

        if (entry.mediaContent.isNotBlank()){
            binding.imageView2.setImageURI(Uri.parse(entry.mediaContent))
        }else{
            binding.imageView2.visibility = View.GONE
        }


    }
}