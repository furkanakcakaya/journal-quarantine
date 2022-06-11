package com.furkanakcakaya.journalquarantine.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.databinding.FragmentViewEntryBinding
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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


        binding.imageView4.setOnClickListener {
            val popup = PopupMenu(requireContext(), binding.imageView4)
            MenuInflater(requireContext()).inflate(R.menu.share_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.share_pdf -> {
                        if (checkPermission()) {
                            createPDF(entry)
                        } else {
                            requestPermission();
                        }
                        true
                    }
                    R.id.share_friend -> {
                        Snackbar.make(requireView(), "Coming soon", Snackbar.LENGTH_SHORT).show()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        }
    }

    private fun checkPermission(): Boolean {
        val permission1 = ContextCompat.checkSelfPermission(requireContext(),WRITE_EXTERNAL_STORAGE)
        val permission2 = ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            200
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 200) {
            if (grantResults.isNotEmpty()) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Snackbar.make(requireView(), "Permission Granted", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(requireView(), "Permission Denied", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createPDF(entry:JournalEntry){
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val title = Paint()

        val mypageInfo = PageInfo.Builder(792, 1120, 1).create()

        val myPage = pdfDocument.startPage(mypageInfo)

        val canvas = myPage.canvas

        if(entry.mediaContent.isNotBlank()){
            val bmp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, Uri.parse(entry.mediaContent))
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, Uri.parse(entry.mediaContent))
            }
            val scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);
            canvas.drawBitmap(scaledbmp, 0f, 0f, paint)

        }

        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        title.textSize = 24f
        title.color = ContextCompat.getColor(requireContext(), R.color.primaryColor)

        canvas.drawText(entry.title, 209f, 80f, title)

        title.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        title.color = ContextCompat.getColor(requireContext(), R.color.secondaryColor)
        title.textSize = 15f
        title.textAlign = Paint.Align.CENTER
        canvas.drawText(entry.content, 396f, 560f, title)

        pdfDocument.finishPage(myPage)
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "JournalQuarantine")
        val file = File(directory, "${entry.title.lowercase()}.pdf")
        try {
            directory.mkdirs()
        }catch (e:Exception){
            e.printStackTrace()
        }

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(
                this.requireContext(),
                "PDF file generated successfully.",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        pdfDocument.close()
    }
}

