package com.furkanakcakaya.journalquarantine.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.furkanakcakaya.journalquarantine.databinding.FragmentNewEntryBinding
import com.furkanakcakaya.journalquarantine.viewmodels.NewEntryViewModel

class NewEntryFragment : Fragment() {
    private val TAG = "NewEntryFragment"
    private lateinit var binding: FragmentNewEntryBinding
    private lateinit var viewModel: NewEntryViewModel

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "result.data : ${result.data}")
            }
        })
//TODO: Add a resultLauncher for the camera
    //MUTIPLE IMAGES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tmp : NewEntryViewModel by viewModels()
        viewModel = tmp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNewEntryBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.lottieAnimationView.playAnimation()
    }

    fun enterMood(){
        viewModel.addEntry("a","a","a")
    }

    fun selectImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch( Intent.createChooser(intent, "Select Picture"))
    }


    fun addImages(){
        viewModel.addImages()
    }
}