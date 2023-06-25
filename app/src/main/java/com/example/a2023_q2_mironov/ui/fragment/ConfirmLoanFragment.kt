package com.example.a2023_q2_mironov.ui.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentConfirmLoanBinding
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.*
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.*
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanViewModel
import com.example.a2023_q2_mironov.ui.activity.MainActivity
import com.example.a2023_q2_mironov.util.formatAmount
import com.example.a2023_q2_mironov.util.formatPercent
import com.example.a2023_q2_mironov.util.showUnauthorizedDialog
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
        binding.errorButton.setOnClickListener {
            viewModel.backToMain()
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
            contentContainer.visibility = View.VISIBLE
            errorContainer.visibility = View.GONE
            progressBar.visibility = View.GONE
            amountValue.text = formatAmount(requireContext(), request.amount.toLong())
            nameValue.text = request.firstName
            surnameValue.text = request.lastName
            percentValue.text = formatPercent(request.percent)
            phoneNumberValue.text = request.phoneNumber
        }
    }

    private fun launchLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.contentContainer.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
    }

    private fun launchErrorState(type: LoanErrorType) {
        with(binding) {
            progressBar.visibility = View.GONE
            contentContainer.visibility = View.GONE
            errorContainer.visibility = View.VISIBLE
            when (type) {
                UNAUTHORIZED -> showUnauthorizedDialog(requireContext(), viewModel::relogin)

                UNKNOWN -> errorMessage.text = getString(R.string.unknown_error)

                CONNECTION -> {
                    errorMessage.text = getString(R.string.connection_error)
                    errorIcon.setImageResource(R.drawable.ic_connection_error)
                }
            }
        }
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