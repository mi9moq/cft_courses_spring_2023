package com.example.a2023_q2_mironov.ui.create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentCreateLoanBinding
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.*
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Content
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Error
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Initial
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Loading
import com.example.a2023_q2_mironov.presentation.create.CreateLoanViewModel
import com.example.a2023_q2_mironov.ui.activity.MainActivity
import com.example.a2023_q2_mironov.ui.util.addTextWatcher
import com.example.a2023_q2_mironov.ui.util.formatAmount
import com.example.a2023_q2_mironov.ui.util.formatPercent
import com.example.a2023_q2_mironov.ui.util.formatPeriod
import com.example.a2023_q2_mironov.ui.util.showUnauthorizedDialog
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
            tryAgain.setOnClickListener {
                viewModel.loadCondition()
            }
        }
    }

    private fun validateInput() {
        viewModel.errorInputAmount.observe(viewLifecycleOwner) { invalidValue ->
            binding.tilAmount.error = invalidValuedMessage(invalidValue)
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) { invalidValue ->
            binding.tilFirstName.error = emptyFieldMessage(invalidValue)
        }
        viewModel.errorInputSurname.observe(viewLifecycleOwner) { invalidValue ->
            binding.tilLastName.error = emptyFieldMessage(invalidValue)
        }
        viewModel.errorInputPhoneNumber.observe(viewLifecycleOwner) { invalidValue ->
            binding.tilPhoneNumber.error = emptyFieldMessage(invalidValue)
        }
    }

    private fun emptyFieldMessage(invalid: Boolean): String? =
        if (invalid) getString(R.string.empty_field) else null

    private fun invalidValuedMessage(invalid: Boolean): String? =
        if (invalid) getString(R.string.invalid_value) else null

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::launchState)
    }

    private fun launchState(state: CreateLoanState) {
        when (state) {
            Initial -> Unit
            Loading -> launchLoadingState()
            is Content -> launchContentState(state.condition)
            is Error -> launchErrorState(state.type)
        }
    }

    private fun launchContentState(conditions: LoanConditions) {
        with(binding) {
            progressBar.visibility = View.GONE
            contentContainer.visibility = View.VISIBLE
            errorContainer.visibility = View.GONE
            maxAmountValue.text = formatAmount(requireContext(), conditions.maxAmount)
            percentValue.text = formatPercent(conditions.percent)
            periodValue.text = formatPeriod(requireContext(), conditions.period)
        }
    }

    private fun launchLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.contentContainer.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
    }

    private fun launchErrorState(type: LoanErrorType) {
        binding.progressBar.visibility = View.GONE
        binding.contentContainer.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        when (type) {
            UNAUTHORIZED -> showUnauthorizedDialog(requireContext(), viewModel::resetToken)

            UNKNOWN -> binding.errorMessage.text = getString(R.string.unknown_error)

            CONNECTION -> {
                binding.errorMessage.text = getString(R.string.connection_error)
                binding.errorIcon.setImageResource(R.drawable.ic_connection_error)
            }
        }
    }

    private fun addTextChangeListener() {
        with(binding) {
            etAmount.addTextChangedListener(addTextWatcher(viewModel::resetErrorInputAmount))
            etFirstName.addTextChangedListener(addTextWatcher(viewModel::resetErrorInputName))
            etLastName.addTextChangedListener(addTextWatcher(viewModel::resetErrorInputSurname))
            etPhoneNumber.addTextChangedListener(addTextWatcher(viewModel::resetErrorPhoneNumber))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}