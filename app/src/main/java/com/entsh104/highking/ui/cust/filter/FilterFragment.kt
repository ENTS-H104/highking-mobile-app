package com.entsh104.highking.ui.cust.filter

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.ui.util.NavOptionsUtil
import java.util.*

class FilterFragment : Fragment() {

    private val mountainList = listOf(
        "Semeru", "Rinjani", "Kerinci", "Merbabu", "Slamet", "Gede", "Lawu", "Merapi", "Pangrango",
        "Sindoro", "Arjuno", "Welirang", "Sumbing", "Raung", "Agung", "Batur", "Bromo", "Ijen",
        "Papandayan", "Tambora", "Soputan", "Lokon", "Bambapuang", "Gamalama", "Krakatau",
        "Kelimutu", "Bukit Raya", "Mutis", "Cartenz", "Salak", "Dempo", "Sinabung", "Leuser",
        "Dieng", "Talakmau", "Halimun", "Burangrang", "Guntur", "Ciremai", "Latimojong", "Semeru",
        "Palung", "Lewotobi", "Inerie", "Besar", "Seulawah Agam", "Hutapanjang", "Butak",
        "Karangetang", "Tambora"
    )

    private lateinit var actvLocation: AutoCompleteTextView
    private lateinit var tvDate: TextView
    private lateinit var btnSearch: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cust_filter, container, false)
        actvLocation = view.findViewById(R.id.autoCompleteLocation)
        tvDate = view.findViewById(R.id.textViewDate)
        btnSearch = view.findViewById(R.id.btn_search_trip)

        setupAutoCompleteTextView()
        setupDatePicker()
        setupSearchButton()

        return view
    }

    private fun setupAutoCompleteTextView() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mountainList)
        actvLocation.setAdapter(adapter)

        actvLocation.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                actvLocation.showDropDown()
            }
        }

        actvLocation.setOnDismissListener {
            val inputText = actvLocation.text.toString()
            if (!mountainList.contains(inputText)) {
                actvLocation.error = "Mountain not found"
            }
        }
    }

    private fun setupDatePicker() {
        tvDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                tvDate.text = formattedDate
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun setupSearchButton() {
        btnSearch.setOnClickListener {
            val selectedMountain = actvLocation.text.toString()
            val selectedDate = tvDate.text.toString()

            if (!mountainList.contains(selectedMountain)) {
                actvLocation.error = "Mountain not found"
                return@setOnClickListener
            }

            if (selectedDate.isEmpty()) {
                tvDate.error = "Please select a date"
                return@setOnClickListener
            }

            findNavController().navigate(R.id.action_nav_search_to_nav_listTrip, null, NavOptionsUtil.defaultNavOptions)
        }
    }
}
