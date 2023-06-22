package com.example.a2023_q2_mironov.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentConfirmLoanBinding
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.ErrorType.*
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.*
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanViewModel
import com.example.a2023_q2_mironov.util.formatAmount
import com.example.a2023_q2_mironov.util.formatPercent
import javax.inject.Inject

class ConfirmLoanFragment : Fragment() {

    companion object {
        fun newInstance(loanRequest: LoanRequest) = ConfirmLoanFragment().apply {
            arguments = Bundle().apply {
                putParcelable(LOAN, loanRequest)
            }
        }

        private const val LOAN = "loan request"
    }

    private val component by lazy {
        (requireActivity() as MainActivity).component
    }

    private var _binding: FragmentConfirmLoanBinding? = null
    private val binding: FragmentConfirmLoanBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ConfirmLoanViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.confirm.setOnClickListener {
            viewModel.createLoan()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::launchState)
    }

    private fun launchState(state: ConfirmLoanState) {
        when (state) {
            Initial -> Unit
            Loading -> launchLoadingState()
            is Content -> launchContentState(state.loanRequest)
            is Error -> launchErrorState(state.type)
        }
    }

    private fun launchContentState(request: LoanRequest) {
        with(binding) {
            amountValue.text = formatAmount(requireContext(), request.amount.toLong())
            nameValue.text = request.firstName
            surnameValue.text = request.lastName
            percentValue.text = formatPercent(request.percent)
            phoneNumberValue.text = request.phoneNumber
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
            UNAUTHORIZED -> {
                val message = getString(R.string.authorisation_error)
                showToast(message)
            }

            NOT_FOUND -> {
                val message = getString(R.string.not_found_error)
                showToast(message)
            }

            UNKNOWN -> {
                val message = getString(R.string.unknown_error)
                showToast(message)
            }

            CONNECTION -> {
                val message = getString(R.string.connection_error)
                showToast(message)
            }

            REGISTRATION -> Unit
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    @Suppress("DEPRECATION")
    private fun parseArguments() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(LOAN, LoanRequest::class.java)?.let { loanRequest ->
                viewModel.putLoanRequest(loanRequest)
            }
        } else {
            arguments?.getParcelable<LoanRequest>(LOAN)?.let { loanRequest ->
                viewModel.putLoanRequest(loanRequest)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}