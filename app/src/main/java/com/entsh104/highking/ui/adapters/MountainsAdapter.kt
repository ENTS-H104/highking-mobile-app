package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.R
import com.entsh104.highking.ui.model.Mountain

class MountainAdapter(
    private val mountains: List<Mountain>,
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

        fun bind(mountain: Mountain) {
            imageView.setImageResource(mountain.imageResId)
            textViewMountainName.text = mountain.name
            textViewElevation?.text = "${mountain.elevation} MDPL"
            textViewCityName?.text = mountain.city
            textViewOpenTrips?.text = "${mountain.openTrips} Trip Open"
            buttonLove?.setImageResource(
                if (mountain.isLoved) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            )
            buttonLove?.setOnClickListener {
                mountain.isLoved = !mountain.isLoved
                buttonLove.setImageResource(
                    if (mountain.isLoved) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
                )
            }
        }
    }
}
