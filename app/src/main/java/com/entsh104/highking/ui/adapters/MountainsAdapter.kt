package com.entsh104.highking.ui.adapters

import com.entsh104.highking.ui.model.Mountain
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.R

class MountainsAdapter(private val mountains: List<Mountain>) :
    RecyclerView.Adapter<MountainsAdapter.MountainsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mountain, parent, false)
        return MountainsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MountainsViewHolder, position: Int) {
        holder.bind(mountains[position])
    }

    override fun getItemCount(): Int {
        return mountains.size
    }

    inner class MountainsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewMountain)
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewMountainName)

        fun bind(mountain: Mountain) {
            imageView.setImageResource(mountain.imageResId)
            nameTextView.text = mountain.name
        }
    }
}
