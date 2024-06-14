package com.entsh104.highking.ui.cust.order

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.model.OpenTripDetail
import com.entsh104.highking.databinding.FragmentCustOrderDetailsBinding
import com.entsh104.highking.ui.util.NavOptionsUtil
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.widget.ScrollView
import androidx.core.content.FileProvider

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentCustOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var trip: OpenTripDetail
    private val args: OrderDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trip = args.trip

        // Set up view with order details
        setupOrderDetails()

        // Set up print ticket button
        binding.btnPrintTicket.setOnClickListener {
            printTicket()
        }

        binding.btnBack.setOnClickListener {
            val action = OrderDetailsFragmentDirections.actionOrderDetailsFragmentToNavOrders()
            it.findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
        }
    }

    private fun setupOrderDetails() {
        Glide.with(this@OrderDetailsFragment).load(trip.image_url).into(binding.imageViewMountain)
        binding.textViewTripName.text = trip.open_trip_name
        binding.textViewHikerName.text = "HighKing"
        binding.textViewDepartureDate.text = trip.schedule_data[0].start_date
        binding.textViewPickupLocation.text = trip.mountain_data[0].name
        binding.textViewContactInfo.text = trip.mitra_data[0].username
        val barcodeBitmap = generateBarcode(trip.open_trip_uuid)
        if (barcodeBitmap != null) {
            binding.imageViewBarcode.setImageBitmap(barcodeBitmap)
        }
        binding.btnPrintTicket.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_PERMISSION)
                } else {
                    printTicket()
                }
            } else {
                printTicket()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                printTicket()
            } else {
                Toast.makeText(context, "Write permission is required to save PDF", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun generateBarcode(value: String): Bitmap? {
        return try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, 600, 600)
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: Exception) {
            null
        }
    }


    private fun printTicket() {
        val totalHeight = getScrollViewHeight(binding.scrollView)

        // Create a PdfDocument object
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(binding.scrollView.width, totalHeight, 1).create()
        val page = document.startPage(pageInfo)

        val canvas: Canvas = page.canvas
        binding.scrollView.draw(canvas)
        document.finishPage(page)

        val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val subDirectory = File(directory, "HighKing")
        if (!subDirectory.exists()) {
            subDirectory.mkdirs()
        }

        val filePath = getUniqueFileName(subDirectory, "OrderDetails", ".pdf")

        try {
            document.writeTo(FileOutputStream(filePath))
            openPDF(filePath)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving PDF: " + e.message, Toast.LENGTH_LONG).show()
        }
        document.close()
    }

    private fun getScrollViewHeight(scrollView: ScrollView): Int {
        var totalHeight = 0
        for (i in 0 until scrollView.childCount) {
            totalHeight += scrollView.getChildAt(i).height
        }
        return totalHeight
    }
    private fun openPDF(file: File) {
        val uri: Uri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No application found to open PDF", Toast.LENGTH_LONG).show()
        }
    }

    private fun getUniqueFileName(directory: File, baseName: String, extension: String): File {
        var file = File(directory, "$baseName$extension")
        var index = 1

        while (file.exists()) {
            file = File(directory, "$baseName($index)$extension")
            index++
        }

        return file
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_WRITE_PERMISSION = 1
    }
}
