package com.example.a2023_q2_mironov.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentHistoryBinding
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.CONNECTION
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.UNAUTHORIZED
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.UNKNOWN
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.history.HistoryState
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Content
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Error
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Initial
import com.example.a2023_q2_mironov.presentation.history.HistoryState.Loading
import com.example.a2023_q2_mironov.presentation.history.HistoryViewModel
import com.example.a2023_q2_mironov.ui.activity.MainActivity
import com.example.a2023_q2_mironov.ui.history.adapter.HistoryAdapter
import com.example.a2023_q2_mironov.ui.util.showUnauthorizedDialog
import javax.inject.Inject

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private val component by lazy {
        (requireActivity() as MainActivity).component
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]
    }

    private val historyAdapter = HistoryAdapter { viewModel.openLoanDetail(it.id) }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        observeViewModel()
    }

    private fun setupClickListener() {
        binding.createLoan.setOnClickListener {
            viewModel.openCreate()
        }
        binding.refresh.setOnClickListener {
            viewModel.loadHistory()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::launchState)
    }

    private fun launchState(state: HistoryState) {
        when (state) {
            Initial -> Unit
            Loading -> launchLoadingState()
            is Content -> launchContentState(state.loans)
            is Error -> launchErrorState(state.type)
        }
    }

    private fun launchContentState(loans: List<Loan>) {
        with(binding) {
            progressBar.visibility = View.GONE
            errorContainer.visibility = View.GONE
            if (loans.isEmpty()) {
                historyList.visibility = View.GONE
                noLoans.visibility = View.VISIBLE
                createLoan.visibility = View.VISIBLE
            } else {
                noLoans.visibility = View.GONE
                createLoan.visibility = View.GONE
                historyList.visibility = View.VISIBLE
                historyList.adapter = historyAdapter
                historyAdapter.submitList(loans)
            }
        }
    }

    private fun launchErrorState(type: LoanErrorType) {
        with(binding) {
            errorContainer.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            historyList.visibility = View.GONE
            noLoans.visibility = View.GONE
            createLoan.visibility = View.GONE
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

    private fun launchLoadingState() {
        with(binding) {
            historyList.visibility = View.GONE
            noLoans.visibility = View.GONE
            createLoan.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            errorContainer.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}