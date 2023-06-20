package com.example.a2023_q2_mironov.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.databinding.FragmentConfirmLoanBinding
import com.example.a2023_q2_mironov.domain.entity.LoanRequest
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanState.*
import com.example.a2023_q2_mironov.presentation.confirm.ConfirmLoanViewModel
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
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                Initial -> Unit
                Loading -> Unit //TODO
                is Content -> launchContentState(state.loanRequest)
                is Error -> Unit //TODO
            }
        }
    }

    private fun launchContentState(request: LoanRequest) {
        with(binding) {
            amountValue.text = request.amount.toString()
            nameValue.text = request.firstName
            surnameValue.text = request.lastName
            percentValue.text = request.percent.toString()
            phoneNumberValue.text = request.phoneNumber
        }
    }

    @Suppress("DEPRECATION")
    private fun parseArguments() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(LOAN, LoanRequest::class.java)?.let { loanRequest ->
                Log.d("parseArguments", loanRequest.toString())
                viewModel.putLoanRequest(loanRequest)
            }
        } else {
            arguments?.getParcelable<LoanRequest>(LOAN)?.let { loanRequest ->
                Log.d("parseArguments", loanRequest.toString())
                viewModel.putLoanRequest(loanRequest)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}