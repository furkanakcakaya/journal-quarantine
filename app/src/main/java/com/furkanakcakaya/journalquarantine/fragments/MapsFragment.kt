package com.furkanakcakaya.journalquarantine.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.databinding.FragmentMapsBinding
import com.furkanakcakaya.journalquarantine.viewmodels.MapsViewModel
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsFragment : Fragment() {
    private lateinit var viewModel: MapsViewModel
    private lateinit var binding:FragmentMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->
        //observe livedata in viewmodel
        viewModel.entries.observe(viewLifecycleOwner) {
            it.forEach {
                if ((it.latitude != null) && (it.longitude != null)) {
                    googleMap.addMarker(
                        com.google.android.gms.maps.model.MarkerOptions()
                            .position(com.google.android.gms.maps.model.LatLng(it.latitude!!, it.longitude!!))
                            .title(it.title)
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tmp:MapsViewModel by viewModels()
        viewModel = tmp
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_maps, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}