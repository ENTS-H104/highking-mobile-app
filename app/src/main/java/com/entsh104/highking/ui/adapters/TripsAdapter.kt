// ui/adapters/TripsAdapter.kt
package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.entsh104.highking.R
import com.entsh104.highking.data.mapper.TripMapper
import com.entsh104.highking.data.model.TripFilter
import com.entsh104.highking.data.viewmodel.TripViewModel
import com.entsh104.highking.ui.cust.trip.ListTripFragmentDirections
import java.text.DecimalFormat


class TripsAdapter(var trips: List<TripFilter>, private val isHorizontal: Boolean = false, private val viewModel: TripViewModel) :
    RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view, isHorizontal)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    inner class TripViewHolder(itemView: View, private val isHorizontal: Boolean) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewTrip)
        private val buttonLove: ImageButton = itemView.findViewById(R.id.buttonLove)
        private val textViewTripName: TextView = itemView.findViewById(R.id.textViewTripName)
        private val textViewMountainName: TextView = itemView.findViewById(R.id.textViewMountainName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        private val textViewCapacity: TextView = itemView.findViewById(R.id.textViewCapacity)

        fun bind(trip: TripFilter) {
            val mPrice = trip.price
            val mCurrencyFormat = DecimalFormat("#,###")
            val myFormattedPrice: String = mCurrencyFormat.format(mPrice)

            Glide.with(itemView.context).load(trip.image_url).into(imageView)
            textViewTripName.text = trip.name
            textViewMountainName.text = trip.mountain_name
            textViewPrice.text = "Rp $myFormattedPrice"
            textViewCapacity.text = trip.total_participants ?: "0"

            // Adjust item layout params for horizontal orientation
            if (isHorizontal) {
                val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.width = (itemView.context.resources.displayMetrics.widthPixels / 2) - 24
                itemView.layoutParams = layoutParams
            }

            viewModel.favoriteTrips.observeForever { favoriteTrips ->
                val isLoved = favoriteTrips.any { it.openTripUuid == trip.open_trip_uuid }
                buttonLove.setImageResource(
                    if (isLoved) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                )
            }

            buttonLove.setOnClickListener {
                val tripEntity = TripMapper.mapFilterToEntity(trip)

                if (viewModel.favoriteTrips.value?.any { it.openTripUuid == trip.open_trip_uuid } == true) {
                    viewModel.deleteTrip(tripEntity)
                    buttonLove.setImageResource(R.drawable.ic_heart_outline)
                } else {
                    viewModel.insertTrip(tripEntity)
                    buttonLove.setImageResource(R.drawable.ic_heart_filled)
                }
            }

            itemView.setOnClickListener {
                val action = ListTripFragmentDirections.actionListTripToDetailTrip(trip.open_trip_uuid)
                itemView.findNavController().navigate(action)
            }
        }
    }
}
