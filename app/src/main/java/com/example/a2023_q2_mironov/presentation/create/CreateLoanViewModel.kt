package com.example.a2023_q2_mironov.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.usecase.GetLoanConditionsUseCase
import com.example.a2023_q2_mironov.domain.usecase.GetUserTokenUseCase
import com.example.a2023_q2_mironov.domain.usecase.ResetUserTokenUseCase
import com.example.a2023_q2_mironov.navigation.router.CreateRouter
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Content
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Error
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Initial
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Loading
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CreateLoanViewModel @Inject constructor(
    private val getLoanConditionUseCase: GetLoanConditionsUseCase,
    private val resetUserTokenUseCase: ResetUserTokenUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val router: CreateRouter,
) : ViewModel() {

    private val _state: MutableLiveData<CreateLoanState> = MutableLiveData(Initial)
    val state: LiveData<CreateLoanState> = _state

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> = _errorInputName

    private val _errorInputSurname = MutableLiveData<Boolean>()
    val errorInputSurname: LiveData<Boolean> = _errorInputSurname

    private val _errorInputAmount = MutableLiveData<Boolean>()
    val errorInputAmount: LiveData<Boolean> = _errorInputAmount

    private val _errorInputPhoneNumber = MutableLiveData<Boolean>()
    val errorInputPhoneNumber: LiveData<Boolean> = _errorInputPhoneNumber

    private val handler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> _state.value =
                Error(LoanErrorType.CONNECTION)

            is HttpException -> {
                if (throwable.code() == 403)
                    _state.value = Error(LoanErrorType.UNAUTHORIZED)
                else
                    _state.value = Error(LoanErrorType.UNKNOWN)
            }

            else -> _state.value = Error(LoanErrorType.UNKNOWN)
        }
    }
    private lateinit var conditions: LoanConditions

    init {
        loadCondition()
    }

    fun loadCondition() {
        val token = getUserTokenUseCase().userToken
        viewModelScope.launch(handler) {
            _state.value = Loading
            conditions = getLoanConditionUseCase(token)
            _state.value = Content(conditions)
        }
    }

    fun resetToken() {
        resetUserTokenUseCase()
        router.openWelcome()
    }

    fun creteLoan(
        inputName: String?,
        inputSurname: String?,
        inputAmount: String?,
        inputNumber: String?
    ) {
        val name = parseInput(inputName)
        val surname = parseInput(inputSurname)
        val amount = parseAmount(inputAmount)
        val phoneNumber = parseInput(inputNumber)

        val fieldsValid = validateInput(name, surname, amount, phoneNumber)

        if (fieldsValid) {
            val request = LoanRequest(
                amount = amount,
                firstName = name,
                lastName = surname,
                period = conditions.period,
                percent = conditions.percent,
                phoneNumber = phoneNumber
            )
            router.openConfirm(request)
        }
    }

    private fun parseInput(input: String?): String = input?.trim() ?: ""

    private fun parseAmount(input: String?): Int {
        return try {
            input?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(
        name: String,
        surname: String,
        amount: Int,
        phoneNumber: String
    ): Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (surname.isBlank()) {
            _errorInputSurname.value = true
            result = false
        }

        if (phoneNumber.isBlank()) {
            _errorInputPhoneNumber.value = true
            result = false
        }

        if (amount <= 0 || amount > conditions.maxAmount) {
            _errorInputAmount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputSurname() {
        _errorInputSurname.value = false
    }

    fun resetErrorInputAmount() {
        _errorInputAmount.value = false
    }

    fun resetErrorPhoneNumber() {
        _errorInputPhoneNumber.value = false
    }
}