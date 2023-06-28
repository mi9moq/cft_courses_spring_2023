package com.example.a2023_q2_mironov.ui.util

import android.content.Context
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.domain.entity.LoanStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatLoanStatus(context: Context, status: LoanStatus) =
    when (status) {
        LoanStatus.APPROVED -> context.getString(R.string.approved)
        LoanStatus.REGISTERED -> context.getString(R.string.registered)
        LoanStatus.REJECTED -> context.getString(R.string.rejected)
    }

fun formatBorrower(context: Context, name: String, surname: String) =
    String.format(context.getString(R.string.name_and_lastname), name, surname)

fun colorStatus(context: Context, status: LoanStatus) =
    when (status) {
        LoanStatus.APPROVED -> context.getColor(R.color.green)
        LoanStatus.REGISTERED -> context.getColor(R.color.purple_700)
        LoanStatus.REJECTED -> context.getColor(R.color.red)
    }

fun formatDate(date: LocalDateTime): String =
    date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

fun formatAmount(context: Context, amount: Long) =
    String.format(context.getString(R.string.amount_format), amount)

fun formatPercent(percent: Double) = "$percent %"

fun formatPeriod(context: Context, period: Int) =
    String.format(context.getString(R.string.period_format), period)