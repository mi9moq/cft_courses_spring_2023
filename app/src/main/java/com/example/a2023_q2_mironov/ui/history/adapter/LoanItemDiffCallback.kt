package com.example.a2023_q2_mironov.ui.history.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.a2023_q2_mironov.domain.entity.Loan

class LoanItemDiffCallback : DiffUtil.ItemCallback<Loan>() {

    override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem == newItem
}