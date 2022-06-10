package com.furkanakcakaya.journalquarantine.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.databinding.FragmentUpdateEntryBinding
import com.furkanakcakaya.journalquarantine.viewmodels.UpdateEntryViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateEntryFragment : Fragment() {
    private lateinit var binding:FragmentUpdateEntryBinding
    private lateinit var viewModel: UpdateEntryViewModel

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.dataString?.let {
                    viewModel.setMediaContent(it)
                    binding.ivMedia.setImageURI(Uri.parse(it))
                }
            }
        })

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
        binding.fragment = this
        viewModel.journalEntry = UpdateEntryFragmentArgs.fromBundle(requireArguments()).entry
        binding.journalEntry = viewModel.journalEntry
        if (viewModel.journalEntry.mediaContent.isNotEmpty()){
            binding.ivMedia.setImageURI(Uri.parse(viewModel.journalEntry.mediaContent))
        }else{
            binding.ivMedia.setImageResource(R.drawable.photo_add)
        }
        binding.lottieAnimationView.setAnimation("${viewModel.journalEntry.mood}.json")
        return binding.root
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
                    Snackbar.make(binding.root, "Mood set to: Happy", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.normal -> {
                    viewModel.setMood("normal")
                    binding.lottieAnimationView.setAnimation("normal.json")
                    binding.lottieAnimationView.playAnimation()
                    Snackbar.make(binding.root, "Mood set to: Normal", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.sad -> {
                    viewModel.setMood("sad")
                    binding.lottieAnimationView.setAnimation("sad.json")
                    binding.lottieAnimationView.playAnimation()
                    Snackbar.make(binding.root, "Mood set to: Sad", Snackbar.LENGTH_SHORT).show()
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
        resultLauncher.launch( Intent.createChooser(intent, "Select Picture"))
    }

    fun addEntry(title: String, content: String){
        if (title.isNotEmpty() && content.isNotEmpty()) {
            viewModel.updateEntry(viewModel.journalEntry.id,title, content)
            Snackbar.make(binding.root, "Update successful", Snackbar.LENGTH_SHORT).show()
            Navigation.findNavController(binding.root).navigate(R.id.action_updateEntryFragment_to_homepageFragment)
        }else{
            Snackbar.make(binding.root, "Please enter a title and content", Snackbar.LENGTH_SHORT).show()
        }
    }
}