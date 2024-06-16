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
    private lateinit var tvDate2: TextView
    private lateinit var btnSearch: Button
    private val mountainMap = mutableMapOf<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cust_filter, container, false)
        actvLocation = view.findViewById(R.id.autoCompleteLocation)
        tvDate = view.findViewById(R.id.textViewDate)
        tvDate2 = view.findViewById(R.id.textViewDate2)
        btnSearch = view.findViewById(R.id.btn_search_trip)

        setupAutoCompleteTextView()
        setupDatePicker()
        setupSearchButton()
        fetchMountains()
        return view
    }

    private fun setupAutoCompleteTextView() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mountainMap.keys.toList())
        actvLocation.setAdapter(adapter)

        actvLocation.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                actvLocation.showDropDown()
            }
        }

        actvLocation.setOnDismissListener {
            val inputText = actvLocation.text.toString()
            if (!mountainMap.containsKey(inputText)) {
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
        tvDate2.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                tvDate2.text = formattedDate
            }, year, month, day)

            datePickerDialog.show()
        }
    }


    private fun setupSearchButton() {
        btnSearch.setOnClickListener {
            val selectedMountain = actvLocation.text.toString()
            val selectedDate = tvDate.text.toString()
            val selectedDate2 = tvDate2.text.toString()

            if (!mountainMap.containsKey(selectedMountain)) {
                actvLocation.error = "Mountain not found"
                return@setOnClickListener
            }

            if (selectedDate.isEmpty()) {
                tvDate.error = "Please select a date"
                return@setOnClickListener
            }

            if (selectedDate2.isEmpty()) {
                tvDate2.error = "Please select a date 2"
                return@setOnClickListener
            }

            val mountainUuid = mountainMap[selectedMountain] ?: return@setOnClickListener
            performSearch(mountainUuid, selectedDate, selectedDate2)
        }
    }

    private fun fetchMountains() {
        viewLifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitClient.getInstance()
            val response = apiService.getMountains()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.forEach {
                    mountainMap[it.name] = it.mountainId
                }
                setupAutoCompleteTextView()
            }
        }
    }

    private fun performSearch(mountainUuid: String, date: String, date2: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            val apiService = RetrofitClient.getInstance()
            val response = apiService.searchOpenTrip(mountainUuid, date, date2)
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

