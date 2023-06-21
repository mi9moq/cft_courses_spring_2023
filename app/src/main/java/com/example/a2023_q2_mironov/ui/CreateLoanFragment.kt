package com.example.a2023_q2_mironov.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentCreateLoanBinding
import com.example.a2023_q2_mironov.domain.entity.LoanConditions
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Content
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Error
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Initial
import com.example.a2023_q2_mironov.presentation.create.CreateLoanState.Loading
import com.example.a2023_q2_mironov.presentation.create.CreateLoanViewModel
import com.example.a2023_q2_mironov.util.addTextWatcher
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
                Loading -> launchLoadingState()
                is Content -> launchContentState(state.condition)
                is Error -> launchErrorState(state.type)
            }
        }
    }

    private fun launchContentState(conditions: LoanConditions) {
        with(binding) {
            progressBar.visibility = View.GONE
            container.visibility = View.VISIBLE
            maxAmountValue.text = conditions.maxAmount.toString()
            percentValue.text = conditions.percent.toString()
            periodValue.text = conditions.period.toString()
        }
    }

    private fun launchLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.container.visibility = View.GONE
    }

    private fun launchErrorState(type: ErrorType) {
        binding.progressBar.visibility = View.GONE
        binding.container.visibility = View.GONE
        when (type) {
            ErrorType.UNAUTHORIZED -> {
                val message = getString(R.string.authorisation_error)
                showToast(message)
            }

            ErrorType.NOT_FOUND -> {
                val message = getString(R.string.not_found_error)
                showToast(message)
            }

            ErrorType.UNKNOWN -> {
                val message = getString(R.string.unknown_error)
                showToast(message)
            }

            ErrorType.CONNECTION -> {
                val message = getString(R.string.connection_error)
                showToast(message)
            }

            ErrorType.REGISTRATION -> Unit
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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