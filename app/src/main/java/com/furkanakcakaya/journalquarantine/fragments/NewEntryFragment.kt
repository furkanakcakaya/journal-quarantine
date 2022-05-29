package com.furkanakcakaya.journalquarantine.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.furkanakcakaya.journalquarantine.databinding.FragmentNewEntryBinding
import com.furkanakcakaya.journalquarantine.viewmodels.NewEntryViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar

class NewEntryFragment : Fragment() {
    private val TAG = "NewEntryFragment"
    private lateinit var binding: FragmentNewEntryBinding
    private lateinit var viewModel: NewEntryViewModel
    private lateinit var flpc:FusedLocationProviderClient
    private var locationPermission = 0

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.dataString?.let { viewModel.setMediaContent(it) }
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tmp : NewEntryViewModel by viewModels()
        viewModel = tmp
        flpc = LocationServices.getFusedLocationProviderClient(requireContext())
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
        //TODO: Popup to choose a mood, 1-2-3
    }

    fun selectImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch( Intent.createChooser(intent, "Select Picture"))
    }

    fun addEntry(title: String, content: String, mood: String){
        if (title.isNotEmpty() && content.isNotEmpty()) {
            locationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            if (locationPermission != PackageManager.PERMISSION_GRANTED ){ //İzin onaylanmamıştır.
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 54)
            }else{//İzin onaylanmıştır.
                viewModel.locationTask = flpc.lastLocation
                viewModel.getLocation()
            }
            viewModel.addEntry(title, content, mood)
        }else{
            Snackbar.make(binding.root, "Please enter a title and content", Snackbar.LENGTH_SHORT).show()
        }
    }
}