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
import com.entsh104.highking.data.model.MountainResponse
import com.entsh104.highking.ui.cust.mountain.ListMountainFragmentDirections
import com.entsh104.highking.ui.util.NavOptionsUtil

class MountainAdapter(
    private val mountains: List<MountainResponse>,
    private val isSimpleLayout: Boolean
) : RecyclerView.Adapter<MountainAdapter.MountainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainViewHolder {
        val layout = if (isSimpleLayout) {
            R.layout.item_mountain
        } else {
            R.layout.item_mountain_card
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MountainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MountainViewHolder, position: Int) {
        holder.bind(mountains[position])
    }

    override fun getItemCount(): Int {
        return mountains.size
    }

    inner class MountainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewMountain)
        private val textViewMountainName: TextView = itemView.findViewById(R.id.textViewMountainName)
        private val textViewElevation: TextView? = itemView.findViewById(R.id.textViewElevation)
        private val textViewCityName: TextView? = itemView.findViewById(R.id.textViewCityName)
        private val textViewOpenTrips: TextView? = itemView.findViewById(R.id.textViewOpenTrips)
        private val buttonLove: ImageButton? = itemView.findViewById(R.id.buttonLove)
        private val textViewDescription: TextView? = itemView.findViewById(R.id.textViewDescription)
        private val textViewWeather: TextView? = itemView.findViewById(R.id.textViewWeather)
        private val textViewTemperature: TextView? = itemView.findViewById(R.id.textViewTemperature)
        private val textViewTicketPrice: TextView? = itemView.findViewById(R.id.textViewTicketPrice)

        fun bind(mountain: MountainResponse) {
            Glide.with(itemView.context)
                .load(mountain.imageUrl)
                .into(imageView)
            textViewMountainName.text = mountain.name
            textViewElevation?.text = "${mountain.height} MDPL"
            textViewCityName?.text = mountain.province
            textViewOpenTrips?.text = "${mountain.totalTripOpen} Trip Open"
            textViewDescription?.text = mountain.description
            textViewTicketPrice?.text = "Rp ${mountain.harga}"

            itemView.setOnClickListener {
                val action = ListMountainFragmentDirections.actionListMountainToDetailMountain(mountain.mountainId)
                itemView.findNavController().navigate(action, NavOptionsUtil.defaultNavOptions)
            }
        }
    }
}
