package com.furkanakcakaya.journalquarantine.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.furkanakcakaya.journalquarantine.databinding.FragmentUpdateEntryBinding
import com.furkanakcakaya.journalquarantine.viewmodels.UpdateEntryViewModel

class UpdateEntryFragment : Fragment() {
    private lateinit var binding:FragmentUpdateEntryBinding
    private lateinit var viewModel: UpdateEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tmp : UpdateEntryViewModel by viewModels()
        viewModel = tmp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateEntryBinding.inflate(inflater,container,false)
        viewModel.journalEntry = UpdateEntryFragmentArgs.fromBundle(requireArguments()).entry
        binding.journalEntry = viewModel.journalEntry

        return binding.root
    }
}