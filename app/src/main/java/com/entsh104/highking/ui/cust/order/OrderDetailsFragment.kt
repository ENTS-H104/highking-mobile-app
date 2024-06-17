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
import android.util.Log
import android.widget.ScrollView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.entsh104.highking.data.model.TransactionDetail
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.data.source.remote.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentCustOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var trip: OpenTripDetail
    private val args: OrderDetailsFragmentArgs by navArgs()
    private lateinit var transaction: TransactionDetail
    private lateinit var transactionId: String

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
        transactionId = args.transactionId

        fetchTransactionById(transactionId)

        binding.btnPrintTicket.setOnClickListener {
            printTicket()
        }
    }

    private fun fetchTransactionById(transactionId: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(500)
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scrollViewContent.visibility = View.GONE
                    try {
                        val apiService = RetrofitClient.getInstance()
                        val response = apiService.getTransactionDetail(transactionId)
                        if (response.isSuccessful) {
                            response.body()?.let { transactionDetailResponse ->
                                if (transactionDetailResponse.status == 200) {
                                    transaction = transactionDetailResponse.data.first()
                                    setupOrderDetails()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        transactionDetailResponse.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                binding.progressBar.visibility = View.GONE
                                binding.scrollViewContent.visibility = View.VISIBLE
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Failed to fetch transaction details",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setupOrderDetails() {
        Glide.with(this@OrderDetailsFragment).load(trip.image_url).into(binding.imageViewMountain)
        binding.textViewTripName.text = transaction.name + " - Gunung " + trip.mountain_data[0].name

        val participants = transaction.name_participant.split(",")
        val formattedParticipants = participants.joinToString(separator = "\n")
        binding.textViewHikerName.text = formattedParticipants

        val departureDateText = "${transaction.start_date} ${transaction.start_time}"
        binding.textViewDepartureDate.text = departureDateText

        binding.textViewPickupLocation.text = transaction.meeting_point

        val contactInfoText = "${transaction.phone_number} a.n ${trip.mitra_data[0].username}"
        binding.textViewContactInfo.text = contactInfoText

        val barcodeBitmap = generateBarcode(transactionId)
        Log.d("OrderDetailsFragment", "Barcode bitmap: $barcodeBitmap transaction: $transaction")
        if (barcodeBitmap != null && transaction.status_accepted == "ACCEPTED" && transaction.status_payment == "SUCCESS") {
            binding.imageViewBarcode.setImageBitmap(barcodeBitmap)
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
        } else {
            binding.imageViewBarcode.visibility = View.GONE
            binding.btnPrintTicket.visibility = View.GONE
        }

        binding.btnCallTour.setOnClickListener {
            val phoneNumber = transaction.phone_number.replace("+", "").replace(" ", "")
            val url = "https://wa.me/$phoneNumber"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
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
