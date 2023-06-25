package com.example.a2023_q2_mironov.presentation.history

import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType

sealed interface HistoryState {

    object Initial: HistoryState

    object Loading: HistoryState

    data class Content(val loans: List<Loan>): HistoryState

    data class Error(val type: LoanErrorType): HistoryState
}