package com.entsh104.highking.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entsh104.highking.databinding.ItemReviewBinding
import com.entsh104.highking.ui.model.Review

class ReviewAdapter(private val reviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val reviewItem = reviewList[position]
        holder.bind(reviewItem)
    }

    override fun getItemCount() = reviewList.size

    class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.textViewReviewerName.text = review.reviewerName
            binding.ratingBar.rating = review.rating
            binding.textViewReviewDate.text = review.reviewDate
            binding.textViewReviewText.text = review.reviewText
        }
    }
}
