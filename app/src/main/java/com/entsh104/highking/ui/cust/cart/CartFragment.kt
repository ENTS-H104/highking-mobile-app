package com.entsh104.highking.ui.cust.cart

import UserRepository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.model.CreateTransactionRequest
import com.entsh104.highking.data.model.Participant
import com.entsh104.highking.data.model.TripFilter
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustCartBinding
import com.entsh104.highking.ui.util.NavOptionsUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private var _binding: FragmentCustCartBinding? = null
    private val binding get() = _binding!!
    private val args: CartFragmentArgs by navArgs()
    private lateinit var trip: TripFilter
    private lateinit var userRepository: UserRepository

    private val participants = mutableListOf<Participant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trip = args.trip

        // Use the trip data
        Glide.with(this@CartFragment).load(trip.image_url).into(binding.imageViewTrip)
        binding.tvTripName.text = trip.name
        binding.tvTripPrice.text = "Rp ${trip.price}"
        binding.tvTripLocation.text = trip.mountain_name

        // Initialize the UserRepository
        val prefs = SharedPreferencesManager(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        setupQuantitySpinner()
        setupClickListeners()
    }

    private fun setupQuantitySpinner() {
        binding.spinnerQuantity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val quantity = parent.getItemAtPosition(position).toString().toInt()
                updateHikerItems(quantity)
                updateTotalCost(quantity)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun updateHikerItems(quantity: Int) {
        binding.llHikerItem.removeAllViews()
        participants.clear()
        for (i in 1..quantity) {
            val hikerView = layoutInflater.inflate(R.layout.item_hiker, binding.llHikerItem, false) as LinearLayout
            hikerView.findViewById<TextView>(R.id.tvHikerName).text = "Pendaki $i"
            hikerView.setOnClickListener {
                showHikerInfoDialog(i - 1)
            }
            binding.llHikerItem.addView(hikerView)
            participants.add(Participant("", "", ""))
        }
    }

    private fun updateTotalCost(quantity: Int) {
        val ticketPrice = trip.price
        val totalTicketPrice = ticketPrice * quantity
        binding.tvTotalTiket.text = "Rp $totalTicketPrice"
        binding.tvTotalTagihan.text = "Rp $totalTicketPrice"
    }

    private fun showHikerInfoDialog(position: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_hiker_info, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextKTP = dialogView.findViewById<EditText>(R.id.editTextKTP)
        val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextPhone)

        val participant = participants[position]
        editTextName.setText(participant.name)
        editTextKTP.setText(participant.nik)
        editTextPhone.setText(participant.handphone_number)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val name = editTextName.text.toString().trim()
            val ktp = editTextKTP.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()

            participants[position] = Participant(name, ktp, phone) // Save the participant info

            Toast.makeText(requireContext(), "Info Saved: $name, $ktp, $phone", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
    }

    private fun showPaymentMethodDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_payment_method, null)
        val radioGopay = dialogView.findViewById<RadioButton>(R.id.radioGopay)
        val radioOVO = dialogView.findViewById<RadioButton>(R.id.radioOVO)
        val radioDana = dialogView.findViewById<RadioButton>(R.id.radioDana)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.buttonSelectPaymentMethod).setOnClickListener {
            val selectedPaymentMethod = when {
                radioGopay.isChecked -> "Gopay"
                radioOVO.isChecked -> "OVO"
                radioDana.isChecked -> "Dana"
                else -> ""
            }

            binding.llPaymentMethod.findViewById<TextView>(R.id.tv_payment_method).text = selectedPaymentMethod
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
    }

    private fun showDiscountCodeDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_discount_code, null)
        val editTextDiscountCode = dialogView.findViewById<EditText>(R.id.editTextDiscountCode)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.buttonApplyDiscountCode).setOnClickListener {
            val discountCode = editTextDiscountCode.text.toString().trim()
            Toast.makeText(requireContext(), "Discount Code Applied: $discountCode", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
    }

    private fun setupClickListeners() {
        binding.btnCheckout.setOnClickListener {
            createTransaction()
        }

        binding.llPaymentMethod.setOnClickListener {
            showPaymentMethodDialog()
        }

        binding.llDiscountCode.setOnClickListener {
            showDiscountCodeDialog()
        }
    }

    private fun createTransaction() {
        val userUid = userRepository.getToken()
        val paymentGatewayUuid = "7df37da9-3b00-4c32-b604-5279b8586f45"

        val request = CreateTransactionRequest(
            user_uid = userUid!!,
            open_trip_uuid = trip.open_trip_uuid,
            total_participant = participants.size,
            payment_gateway_uuid = paymentGatewayUuid,
            participants = participants
        )

        lifecycleScope.launch {
            val apiService = RetrofitClient.getInstance()
            val response = apiService.createTransaction(request)
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Transaction created successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_nav_cart_to_confirmationCheckoutFragment)
            } else {
                Toast.makeText(requireContext(), "Failed to create transaction", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

