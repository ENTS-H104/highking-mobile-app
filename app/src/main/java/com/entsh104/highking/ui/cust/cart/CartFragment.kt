package com.entsh104.highking.ui.cust.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.databinding.FragmentCustCartBinding
import com.entsh104.highking.ui.util.NavOptionsUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CartFragment : Fragment() {

    private var _binding: FragmentCustCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup click listeners for buttons and other UI elements
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_nav_cart_to_confirmationCheckoutFragment)
        }

        binding.llPaymentMethod.setOnClickListener {
            // Implement navigation to payment method selection
        }

        binding.llDiscountCode.setOnClickListener {
            // Implement navigation to discount code entry
        }

        binding.llHikerDataItem.setOnClickListener {
            showHikerInfoDialog()
        }
        binding.llHikerDataItem2.setOnClickListener {
            showHikerInfoDialog()
        }

        binding.ivPaymentMethod.setOnClickListener {
            showPaymentMethodDialog()
        }

        binding.ivDiscountCode.setOnClickListener {
            showDiscountCodeDialog()
        }
    }

    private fun showHikerInfoDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_hiker_info, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextKTP = dialogView.findViewById<EditText>(R.id.editTextKTP)
        val editTextPhone = dialogView.findViewById<EditText>(R.id.editTextPhone)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.buttonSave).setOnClickListener {
            val name = editTextName.text.toString().trim()
            val ktp = editTextKTP.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()

            // Here you can save the hiker info or do something with it
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
