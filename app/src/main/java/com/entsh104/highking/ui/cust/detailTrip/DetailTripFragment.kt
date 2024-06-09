package com.entsh104.highking.ui.cust.detailTrip

import UserRepository
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.source.local.SharedPreferencesManager
import com.entsh104.highking.data.source.remote.RetrofitClient
import com.entsh104.highking.databinding.FragmentCustDetailTripBinding
import com.entsh104.highking.ui.util.NavOptionsUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.entsh104.highking.data.model.OpenTripDetail
import com.entsh104.highking.data.model.TripFilter
import java.text.NumberFormat

class DetailTripFragment : Fragment() {

    private var _binding: FragmentCustDetailTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private val args: DetailTripFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustDetailTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tripId = args.tripId

        val prefs = SharedPreferencesManager(requireContext())
        RetrofitClient.createInstance(requireContext())
        userRepository = UserRepository(RetrofitClient.getInstance(), prefs)

        fetchData(tripId)
    }

    private fun fetchData(tripId: String) {
        // Show ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val fetchTripDetailDeferred = async { fetchTripDetail(tripId) }

            fetchTripDetailDeferred.await()

            // Hide ProgressBar
            binding.progressBar.visibility = View.GONE
        }
    }

    private suspend fun fetchTripDetail(tripId: String) {
        val result = userRepository.getOpenTripById(tripId)
        if (result.isSuccess) {
            val tripList = result.getOrNull()?.data
            tripList?.firstOrNull()?.let { trip ->
                Glide.with(this@DetailTripFragment).load(trip.image_url).into(binding.ivTripImage)
                var mPrice = trip.price
                val mCurrencyFormat  = NumberFormat.getCurrencyInstance()
                val myFormattedPrice: String = mCurrencyFormat.format(mPrice)
                binding.tvTripName.text = trip.open_trip_name
                binding.tvTripPrice.text = "Rp ${myFormattedPrice}"
                binding.tvTripAvailability.text = "${trip.min_people}-${trip.max_people}"
                binding.tvTripLocation.text = trip.mountain_data.joinToString(", ") { mountain -> mountain.name }
                binding.tvTripDescription.text = trip.description

                trip.mitra_data?.firstOrNull()?.let { mitra ->
                    Glide.with(this@DetailTripFragment).load(mitra.image_url).into(binding.ivPartnerImage)
                    binding.tvPartnerName.text = mitra.username
                }

                val includeLayout = binding.llIncludeSection
                trip.include?.let {
                    val includeView = layoutInflater.inflate(R.layout.item_dropdown, includeLayout, false)
                    includeView.findViewById<TextView>(R.id.tv_dropdown_title).text = "Include"
                    includeView.findViewById<TextView>(R.id.tv_dropdown_description).text = formatBulletPoints(trip.include)
                    val dropdownItem = includeView.findViewById<LinearLayout>(R.id.ll_item_dropdown)
                    val toggleArrow = includeView.findViewById<ImageView>(R.id.iv_expand_dropdown)
                    val rundownContent = includeView.findViewById<LinearLayout>(R.id.ll_dropdown_content)

                    dropdownItem.setOnClickListener {
                        if (rundownContent.visibility == View.GONE) {
                            rundownContent.visibility = View.VISIBLE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_up)
                        } else {
                            rundownContent.visibility = View.GONE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_down)
                        }
                    }
                    includeLayout.addView(includeView)
                }

                val excludeLayout = binding.llExcludeSection
                trip.exclude?.let {
                    val excludeView = layoutInflater.inflate(R.layout.item_dropdown, excludeLayout, false)
                    excludeView.findViewById<TextView>(R.id.tv_dropdown_title).text = "Exclude"
                    excludeView.findViewById<TextView>(R.id.tv_dropdown_description).text = formatBulletPoints(trip.exclude)
                    val dropdownItem = excludeView.findViewById<LinearLayout>(R.id.ll_item_dropdown)
                    val toggleArrow = excludeView.findViewById<ImageView>(R.id.iv_expand_dropdown)
                    val rundownContent = excludeView.findViewById<LinearLayout>(R.id.ll_dropdown_content)

                    dropdownItem.setOnClickListener {
                        if (rundownContent.visibility == View.GONE) {
                            rundownContent.visibility = View.VISIBLE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_up)
                        } else {
                            rundownContent.visibility = View.GONE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_down)
                        }
                    }
                    excludeLayout.addView(excludeView)
                }

                val rundownLayout = binding.llTripSchedule
                trip.rundown_data?.forEach { rundown ->
                    val rundownView = layoutInflater.inflate(R.layout.item_dropdown, rundownLayout, false)
                    rundownView.findViewById<TextView>(R.id.tv_dropdown_title).text = "Day ${rundown.day}"
                    rundownView.findViewById<TextView>(R.id.tv_dropdown_description).text = rundown.description
                    val dropdownItem = rundownView.findViewById<LinearLayout>(R.id.ll_item_dropdown)
                    val toggleArrow = rundownView.findViewById<ImageView>(R.id.iv_expand_dropdown)
                    val rundownContent = rundownView.findViewById<LinearLayout>(R.id.ll_dropdown_content)

                    dropdownItem.setOnClickListener {
                        if (rundownContent.visibility == View.GONE) {
                            rundownContent.visibility = View.VISIBLE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_up)
                        } else {
                            rundownContent.visibility = View.GONE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_down)
                        }
                    }

                    rundownLayout.addView(rundownView)
                }

                val faqLayout = binding.llFaq
                trip.faq_data?.forEach { faq ->
                    val faqView = layoutInflater.inflate(R.layout.item_dropdown, faqLayout, false)
                    val splitDescription = faq.description.split("Question:", "Answer:")
                    if (splitDescription.size >= 3) {
                        val question = splitDescription[1].trim()
                        val answer = splitDescription[2].trim()
                        faqView.findViewById<TextView>(R.id.tv_dropdown_title).text = question
                        faqView.findViewById<TextView>(R.id.tv_dropdown_description).text = answer
                    }
                    val dropdownItem = faqView.findViewById<LinearLayout>(R.id.ll_item_dropdown)
                    val toggleArrow = faqView.findViewById<ImageView>(R.id.iv_expand_dropdown)
                    val rundownContent = faqView.findViewById<LinearLayout>(R.id.ll_dropdown_content)

                    dropdownItem.setOnClickListener {
                        if (rundownContent.visibility == View.GONE) {
                            rundownContent.visibility = View.VISIBLE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_up)
                        } else {
                            rundownContent.visibility = View.GONE
                            toggleArrow.setImageResource(R.drawable.ic_arrow_down)
                        }
                    }

                    faqLayout.addView(faqView)
                }

                // Convert trip data to TripFilter
                val tripFilter = TripFilter(
                    open_trip_uuid = trip.open_trip_uuid,
                    name = trip.open_trip_name,
                    image_url = trip.image_url,
                    price = trip.price,
                    mountain_name = trip.mountain_data.firstOrNull()?.name ?: "",
                    mountain_uuid = trip.mountain_data.firstOrNull()?.mountain_uuid ?: "",
                    total_participants = null,
                    min_people = trip.min_people,
                    max_people = trip.max_people
                )

                binding.fabCheckoutTrip.setOnClickListener {
                    val action = DetailTripFragmentDirections.actionNavDetailTripToCartFragment(tripFilter)
                    findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
                }

                binding.btnPartnerProfile.setOnClickListener {
                    trip.mitra_data.firstOrNull()?.partner_uid?.let { partnerUid ->
                        val action = DetailTripFragmentDirections.actionNavDetailTripToProfileMitraFragment(partnerUid)
                        findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
                    } ?: run {
                        Toast.makeText(requireContext(), "Mitra ID not found", Toast.LENGTH_SHORT).show()
                    }
                }

                // Share information
                binding.llShareInfo.findViewById<View>(R.id.iv_twitter).setOnClickListener {
                    shareInformation("twitter", trip)
                }
                binding.llShareInfo.findViewById<View>(R.id.iv_facebook).setOnClickListener {
                    shareInformation("facebook", trip)
                }
                binding.llShareInfo.findViewById<View>(R.id.iv_instagram).setOnClickListener {
                    shareInformation("instagram", trip)
                }

                // Handle "baca selengkapnya" click
                binding.tvReadMore.setOnClickListener {
                    // Logic to expand description
                }
            }
        } else {
            Toast.makeText(requireContext(), "Failed to load trip details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatBulletPoints(text: String?): String {
        return text?.split(",")?.joinToString("\n") { "• $it" } ?: ""
    }

    private fun shareInformation(platform: String, trip: OpenTripDetail) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this trip: ${trip.open_trip_name} to ${trip.mountain_data.joinToString(", ") { it.name }}. Price: Rp ${trip.price}. Availability: ${trip.min_people}-${trip.max_people} people!")
            type = "text/plain"
        }

        when (platform) {
            "twitter" -> shareIntent.setPackage("com.twitter.android")
            "facebook" -> shareIntent.setPackage("com.facebook.katana")
            "instagram" -> shareIntent.setPackage("com.instagram.android")
        }

        try {
            startActivity(shareIntent)
        } catch (e: Exception) {
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
