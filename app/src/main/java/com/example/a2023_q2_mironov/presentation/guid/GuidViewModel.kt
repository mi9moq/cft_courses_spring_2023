package com.example.a2023_q2_mironov.presentation.guid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2023_q2_mironov.navigation.router.GuidRouter
import com.example.a2023_q2_mironov.presentation.guid.GuidState.*
import javax.inject.Inject

class GuidViewModel @Inject constructor(
    private val router: GuidRouter
) : ViewModel() {

    private val _state: MutableLiveData<GuidState> = MutableLiveData(Start)
    val state: LiveData<GuidState> = _state

    private var currentState = 1

    fun showNext() {
        when (++currentState) {
            1 -> _state.value = Start
            2 -> _state.value = Fill
            3 -> _state.value = Confirm
            4 -> _state.value = Finish
            else -> {
                router.openMain()
            }
        }
    }

    fun showPrevious() {
        when (--currentState) {
            1 -> _state.value = Start
            2 -> _state.value = Fill
            3 -> _state.value = Confirm
            4 -> _state.value = Finish
            else -> {
                router.openMain()
            }
        }
    }
}