package com.example.a2023_q2_mironov.ui.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.a2023_q2_mironov.domain.entity.Loan

class HistoryAdapter(
    private val onClick: (Loan) -> Unit
) : ListAdapter<Loan, HistoryViewHolder>(LoanItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(parent, onClick)

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}