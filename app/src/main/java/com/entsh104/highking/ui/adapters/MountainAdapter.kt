package com.entsh104.highking.ui.adapters

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.ui.model.Mountain

class MountainAdapter(mountains: List<Mountain>) :
    RecyclerView.Adapter<MountainAdapter.MountainViewHolder>() {
    private val mountains: List<Mountain>

    init {
        this.mountains = mountains
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_mountain, parent, false)
        return MountainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MountainViewHolder, position: Int) {
        val mountain: Mountain = mountains[position]
        holder.imageView.setImageResource(mountain.getImageResId())
        holder.textView.setText(mountain.getName())
    }

    override fun getItemCount(): Int {
        return mountains.size
    }

    internal class MountainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView: TextView

        init {
            imageView = itemView.findViewById<ImageView>(R.id.imageViewMountain)
            textView = itemView.findViewById<TextView>(R.id.textViewMountainName)
        }
    }
}
