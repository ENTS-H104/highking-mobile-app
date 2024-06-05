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

    private lateinit var autoCompleteLocation: AutoCompleteTextView
    private lateinit var textViewDate: TextView
    private lateinit var btnSearchTrip: Button
    private val mountainList = listOf(
        "Semeru",
        "Rinjani",
        "Kerinci",
        "Merbabu",
        "Slamet",
        "Gede",
        "Lawu",
        "Merapi",
        "Pangrango",
        "Sindoro",
        "Arjuno",
        "Welirang",
        "Sumbing",
        "Raung",
        "Agung",
        "Batur",
        "Bromo",
        "Ijen",
        "Papandayan",
        "Tambora",
        "Soputan",
        "Lokon",
        "Bambapuang",
        "Gamalama",
        "Krakatau",
        "Kelimutu",
        "Bukit Raya",
        "Mutis",
        "Cartenz",
        "Salak",
        "Dempo",
        "Sinabung",
        "Leuser",
        "Dieng",
        "Talakmau",
        "Halimun",
        "Burangrang",
        "Guntur",
        "Ciremai",
        "Latimojong",
        "Semeru",
        "Palung",
        "Lewotobi",
        "Inerie",
        "Besar",
        "Seulawah Agam",
        "Hutapanjang",
        "Butak",
        "Karangetang",
        "Tambora"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cust_filter, container, false)

        autoCompleteLocation = view.findViewById(R.id.autoCompleteLocation)
        textViewDate = view.findViewById(R.id.textViewDate)
        btnSearchTrip = view.findViewById(R.id.btn_search_trip)

        // Setup AutoCompleteTextView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mountainList)
        autoCompleteLocation.setAdapter(adapter)
        autoCompleteLocation.threshold = 1

        // Setup DatePickerDialog
        textViewDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Setup Search Button
        btnSearchTrip.setOnClickListener {
            findNavController().navigate(R.id.action_nav_search_to_nav_listTrip, null, NavOptionsUtil.defaultNavOptions)
        }

        return view
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            textViewDate.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }
}
