package com.entsh104.highking.ui.cust.review
import com.entsh104.highking.ui.adapters.ReviewAdapter
import com.entsh104.highking.ui.model.Review
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.entsh104.highking.databinding.FragmentCustReviewBinding

class FragmentCustReview : Fragment() {

    private var _binding: FragmentCustReviewBinding? = null
    private val binding get() = _binding!!

    private val reviewList = listOf(
        Review("Alex K.", 5.0f, "1 week ago", "Lit trip! The views were fire and the guide was super chill."),
        Review("Taylor R.", 4.5f, "2 weeks ago", "Had an amazing time, but the hike was kinda tough. Worth it tho!"),
        Review("Jordan P.", 3.0f, "3 weeks ago", "It was okay. The hike was cool but too many bugs, not a vibe."),
        Review("Morgan S.", 2.0f, "1 month ago", "Lowkey disappointed. The weather was trash and the guide wasn't helpful."),
        Review("Riley B.", 4.0f, "3 days ago", "Pretty dope experience. Met some cool people and the mountain was stunning."),
        Review("Casey J.", 5.0f, "2 days ago", "Absolute banger of a trip! Everything was on point, highly recommend."),
        Review("Jamie L.", 3.5f, "2 weeks ago", "It was alright. The views were nice but the hike was exhausting."),
        Review("Avery M.", 1.5f, "1 month ago", "Not worth it. The hike was way too hard and the facilities were meh."),
        Review("Quinn T.", 4.5f, "4 days ago", "Great trip, had a blast! The guide was super friendly and the scenery was insane."),
        Review("Dakota F.", 5.0f, "5 days ago", "Best trip ever! The mountains were breathtaking and the group was fun af.")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewReviews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ReviewAdapter(reviewList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
