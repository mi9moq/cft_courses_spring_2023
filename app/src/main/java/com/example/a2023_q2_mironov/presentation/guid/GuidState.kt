package com.example.a2023_q2_mironov.presentation.guid

sealed interface GuidState {

    object Start : GuidState

    object Fill : GuidState

    object Confirm : GuidState

    object Finish : GuidState
}