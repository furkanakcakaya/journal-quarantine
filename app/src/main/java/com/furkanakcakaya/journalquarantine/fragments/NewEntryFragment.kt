package com.furkanakcakaya.journalquarantine.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.databinding.FragmentNewEntryBinding
import com.furkanakcakaya.journalquarantine.viewmodels.NewEntryViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.io.File

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
                val intent = result.data
                if (intent != null) {
                    val uri = intent.dataString
                    if (uri != null) {
                        binding.ivAddMedia.setImageURI(Uri.parse(uri))
                        viewModel.setMediaContent(uri)
                    }
                }
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
        val popup = PopupMenu(requireContext(), binding.lottieAnimationView)
        MenuInflater(requireContext()).inflate(R.menu.mood_menu, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.happy -> {
                    viewModel.setMood("happy")
                    binding.lottieAnimationView.setAnimation("happy.json")
                    binding.lottieAnimationView.playAnimation()
                    Snackbar.make(binding.root, getString(R.string.mood_set_happy), Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.normal -> {
                    viewModel.setMood("normal")
                    binding.lottieAnimationView.setAnimation("normal.json")
                    binding.lottieAnimationView.playAnimation()
                    Snackbar.make(binding.root, getString(R.string.mood_set_normal), Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.sad -> {
                    viewModel.setMood("sad")
                    binding.lottieAnimationView.setAnimation("sad.json")
                    binding.lottieAnimationView.playAnimation()
                    Snackbar.make(binding.root, getString(R.string.mood_set_sad), Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    viewModel.setMood("normal")
                    binding.lottieAnimationView.setAnimation("mood.json")
                    binding.lottieAnimationView.playAnimation()
                    true
                }
            }
        }
    }

    fun selectImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        resultLauncher.launch( Intent.createChooser(intent, getString(R.string.select_picture)))
    }

    fun addEntry(title: String, content: String){
        if (title.isNotEmpty() && content.isNotEmpty()) {
            locationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            if (locationPermission != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 54)
            }else{
                viewModel.locationTask = flpc.lastLocation
                viewModel.getLocation()
            }
            viewModel.addEntry(title, content)
            Navigation.findNavController(binding.root).navigate(R.id.action_newEntryFragment_to_homepageFragment)
        }else{
            Snackbar.make(binding.root, getString(R.string.enter_title_content), Snackbar.LENGTH_SHORT).show()
        }
    }
}