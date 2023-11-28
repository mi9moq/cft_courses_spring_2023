package com.example.a2023_q2_mironov.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.LoanHistoryItemBinding
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.ui.util.colorStatus
import com.example.a2023_q2_mironov.ui.util.formatAmount
import com.example.a2023_q2_mironov.ui.util.formatBorrower
import com.example.a2023_q2_mironov.ui.util.formatDate
import com.example.a2023_q2_mironov.ui.util.formatLoanStatus

class HistoryViewHolder(
    parent: ViewGroup,
    private val onClick: (Loan) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.loan_history_item, parent, false)
) {

    private val binding = LoanHistoryItemBinding.bind(itemView)

    fun bind(loan: Loan) {
        with(binding) {
            amountValue.text = formatAmount(itemView.context, loan.amount)
            date.text = formatDate(loan.date)
            statusValue.text = formatLoanStatus(itemView.context, loan.status)
            statusValue.setTextColor(colorStatus(itemView.context, loan.status))
            borrowerValue.text = formatBorrower(itemView.context, loan.firstName, loan.lastName)
        }
        itemView.setOnClickListener {
            onClick(loan)
        }
    }
}