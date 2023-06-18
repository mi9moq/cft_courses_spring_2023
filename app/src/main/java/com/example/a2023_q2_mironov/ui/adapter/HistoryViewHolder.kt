package com.example.a2023_q2_mironov.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.LoanHistoryItemBinding
import com.example.a2023_q2_mironov.domain.entity.Loan

class HistoryViewHolder(
    parent: ViewGroup,
    private val onClick: (Loan) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.loan_history_item, parent, false)
) {

    private val binding = LoanHistoryItemBinding.bind(itemView)

    fun bind(loan: Loan){
        with(binding){
            amount.text = loan.amount.toString()
            date.text = loan.date
            status.text = loan.status
        }
        itemView.setOnClickListener {
            onClick(loan)
        }
    }
}