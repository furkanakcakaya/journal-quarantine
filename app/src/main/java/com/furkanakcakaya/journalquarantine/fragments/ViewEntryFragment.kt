package com.furkanakcakaya.journalquarantine.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.StaticLayout
import android.text.TextPaint
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
        if (entry.mood.isNotBlank()){
            binding.lottieMood.setAnimation("${entry.mood}.json")
            binding.lottieMood.playAnimation()
        }

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
                        Snackbar.make(requireView(), getString(R.string.coming_soon), Snackbar.LENGTH_SHORT).show()
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
                    Snackbar.make(requireView(), getString(R.string.permission_granted), Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(requireView(), getString(R.string.permission_denied), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createPDF(entry:JournalEntry){
        val pdfDocument = PdfDocument()
        val textPaint = Paint()
        val mypageInfo = PageInfo.Builder(792, 1120, 1).create()
        val myPage = pdfDocument.startPage(mypageInfo)
        val canvas = myPage.canvas
        if(entry.mediaContent.isNotBlank()){
            val bmp = BitmapFactory.decodeFile(entry.mediaContent)
            val scaled = Bitmap.createScaledBitmap(bmp, bmp.width/2, bmp.height/2, false)
            canvas.drawBitmap(scaled, 396f-bmp.width/4f, 1080f-bmp.height/2, null)

        }

        textPaint.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        textPaint.textSize = 36f
        textPaint.color = ContextCompat.getColor(requireContext(), R.color.primaryColor)
        canvas.drawText(entry.title.uppercase(), 120F, 110f, textPaint)

        textPaint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        textPaint.color = ContextCompat.getColor(requireContext(), R.color.secondaryColor)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 18f
        canvas.drawText(entry.createdAt, 620f, 80f, textPaint)
        canvas.drawText(entry.locationName, 620f, 100f, textPaint)

//        textPaint.textSize = 24f
//        canvas.drawText(entry.content, 120F, 160f, textPaint)

        val mTextLayout = StaticLayout.Builder.obtain(entry.content, 0, entry.content.length, TextPaint(), 592).build()
        canvas.save()
        val textX = 100f
        val textY = 160f
        canvas.translate(textX, textY);

        mTextLayout.draw(canvas);
        canvas.restore();

        pdfDocument.finishPage(myPage)
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "JournalQuarantine")
        val file = File(directory, "${entry.id}-${entry.title.lowercase()}.pdf")
        try {
            directory.mkdirs()
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(
                this.requireContext(),
                getString(R.string.pdf_success),
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        pdfDocument.close()
    }
}

