package com.furkanakcakaya.journalquarantine.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class NewEntryFragment : Fragment() {
    private val TAG = "NewEntryFragment"
    private lateinit var binding: FragmentNewEntryBinding
    private lateinit var viewModel: NewEntryViewModel
    private lateinit var flpc:FusedLocationProviderClient
    private var locationPermission = 0

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents(),
        ActivityResultCallback { result ->
            viewModel.addMediaContent(result)
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
        val type = "image/*"
        resultLauncher.launch(type)
    }

    fun addEntry(title: String, content: String, mood: String){
        locationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermission != PackageManager.PERMISSION_GRANTED ){ //İzin onaylanmamıştır.
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 54)
        }else{//İzin onaylanmıştır.
            viewModel.locationTask = flpc.lastLocation
            viewModel.getLocation()
        }
        viewModel.addEntry(title, content, mood)
    }
}