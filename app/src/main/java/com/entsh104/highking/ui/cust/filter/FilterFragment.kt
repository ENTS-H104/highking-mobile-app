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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.R
import com.entsh104.highking.ui.util.NavOptionsUtil
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.ui.cust.filter.FilterFragmentDirections
import kotlinx.coroutines.launch
import java.util.*

class FilterFragment : Fragment() {
    private lateinit var actvLocation: AutoCompleteTextView
    private lateinit var tvDate: TextView
    private lateinit var btnSearch: Button
    private val mountainList = mutableListOf<String>()

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
        fetchMountains()
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
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
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

            performSearch(selectedMountain, selectedDate)
        }
    }

    private fun fetchMountains() {
        lifecycleScope.launch {
            val apiService = RetrofitClient.getInstance()
            val response = apiService.getMountains()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.forEach {
                    mountainList.add(it.name)
                }
                setupAutoCompleteTextView()
            }
        }
    }

    private fun performSearch(mountainName: String, date: String) {
        lifecycleScope.launch {
            val apiService = RetrofitClient.getInstance()
            val response = apiService.searchOpenTrip(mountainName, date)
            if (response.isSuccessful && response.body() != null) {
                val searchResults = response.body()?.data
                val action = FilterFragmentDirections.actionNavSearchToNavListTrip(
                    searchResults?.toTypedArray() ?: emptyArray()
                )
                findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }
        }
    }
}
