package com.entsh104.highking.ui.ticket

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.entsh104.highking.data.model.TransactionHistory
import com.entsh104.highking.ui.cust.ticket.AcceptedFragment
import com.entsh104.highking.ui.cust.ticket.CanceledFragment
import com.entsh104.highking.ui.cust.ticket.PendingFragment

class TicketPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var pendingTransactions: List<TransactionHistory> = emptyList()
    private var acceptedTransactions: List<TransactionHistory> = emptyList()
    private var canceledTransactions: List<TransactionHistory> = emptyList()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingFragment.newInstance(pendingTransactions)
            1 -> AcceptedFragment.newInstance(acceptedTransactions)
            2 -> CanceledFragment.newInstance(canceledTransactions)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    fun setPendingTransactions(transactions: List<TransactionHistory>) {
        pendingTransactions = transactions
        notifyDataSetChanged()
    }

    fun setAcceptedTransactions(transactions: List<TransactionHistory>) {
        acceptedTransactions = transactions
        notifyDataSetChanged()
    }

    fun setCanceledTransactions(transactions: List<TransactionHistory>) {
        canceledTransactions = transactions
        notifyDataSetChanged()
    }
}
