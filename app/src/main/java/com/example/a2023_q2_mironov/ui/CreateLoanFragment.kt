package com.example.a2023_q2_mironov.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentCreateLoanBinding
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Content
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Error
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Initial
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Loading
import com.example.a2023_q2_mironov.presentation.create.CreateLoanViewModel
import javax.inject.Inject

class CreateLoanFragment : Fragment() {

    companion object {
        fun newInstance() = CreateLoanFragment()
    }

    private val component by lazy {
        (requireActivity() as MainActivity).component
    }

    private var _binding: FragmentCreateLoanBinding? = null
    private val binding: FragmentCreateLoanBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CreateLoanViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        validateInput()
        addTextChangeListener()
        observeViewModel()
    }

    private fun setupClickListeners() {
        with(binding) {
            next.setOnClickListener {
                viewModel.creteLoan(
                    inputName = etFirstName.text.toString(),
                    inputSurname = etLastName.text.toString(),
                    inputAmount = etAmount.text.toString(),
                    inputNumber = etPhoneNumber.text.toString()
                )
            }
        }
    }

    private fun validateInput() {
        viewModel.errorInputAmount.observe(viewLifecycleOwner) { invalidValue ->
            val message = if (invalidValue) {
                getString(R.string.invalid_value)
            } else {
                null
            }
            binding.tilAmount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) { invalidValue ->
            val message = if (invalidValue) {
                getString(R.string.empty_field)
            } else {
                null
            }
            binding.tilFirstName.error = message
        }
        viewModel.errorInputSurname.observe(viewLifecycleOwner) { invalidValue ->
            val message = if (invalidValue) {
                getString(R.string.empty_field)
            } else {
                null
            }
            binding.tilLastName.error = message
        }
        viewModel.errorInputPhoneNumber.observe(viewLifecycleOwner) { invalidValue ->
            val message = if (invalidValue) {
                getString(R.string.empty_field)
            } else {
                null
            }
            binding.tilPhoneNumber.error = message
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                Initial -> Unit
                Loading -> Unit //TODO обработать загрузку
                is Content -> launchContentState(state.condition)
                is Error -> Unit //TODO обработать ошибки
            }
        }
    }

    private fun launchContentState(conditions: LoanConditions) {
        with(binding) {
            maxAmountValue.text = conditions.maxAmount.toString()
            percentValue.text = conditions.percent.toString()
            periodValue.text = conditions.period.toString()
        }
    }

    private fun addTextChangeListener() {
        with(binding) {
            etAmount.addTextChangedListener(addTextWatcher { viewModel.resetErrorInputAmount() })
            etFirstName.addTextChangedListener(addTextWatcher { viewModel.resetErrorInputName() })
            etLastName.addTextChangedListener(addTextWatcher { viewModel.resetErrorInputSurname() })
            etPhoneNumber.addTextChangedListener(addTextWatcher { viewModel.resetErrorPhoneNumber() })
        }
    }

    private fun addTextWatcher(textChange: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textChange()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}