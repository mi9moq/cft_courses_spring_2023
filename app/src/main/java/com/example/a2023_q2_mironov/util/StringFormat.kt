package com.example.a2023_q2_mironov.util

import android.content.Context
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.domain.entity.LoanStatus

fun formatLoanStatus(context: Context, status: LoanStatus) =
    when (status) {
        LoanStatus.APPROVED -> context.getString(R.string.approved)
        LoanStatus.REGISTERED -> context.getString(R.string.registered)
        LoanStatus.REJECTED -> context.getString(R.string.rejected)
    }
