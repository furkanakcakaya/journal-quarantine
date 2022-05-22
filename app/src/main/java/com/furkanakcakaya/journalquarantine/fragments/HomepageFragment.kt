package com.furkanakcakaya.journalquarantine.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.adapters.JournalAdapter
import com.furkanakcakaya.journalquarantine.databinding.FragmentHomepageBinding
import com.furkanakcakaya.journalquarantine.viewmodels.HomepageViewModel


class HomepageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var viewModel: HomepageViewModel

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
            binding.adapter = JournalAdapter(requireContext(),it)
        }

        return binding.root
    }
}