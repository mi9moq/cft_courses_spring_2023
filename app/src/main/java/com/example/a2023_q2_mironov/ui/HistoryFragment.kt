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
import com.example.a2023_q2_mironov.databinding.FragmentHistoryBinding
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.history.HistoryState
import com.example.a2023_q2_mironov.presentation.history.HistoryState.*
import com.example.a2023_q2_mironov.presentation.history.HistoryViewModel
import com.example.a2023_q2_mironov.ui.adapter.HistoryAdapter
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
        observeViewModel()
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
        binding.progressBar.visibility = View.GONE
        binding.historyList.visibility = View.VISIBLE
        if (loans.isEmpty()) {
            binding.historyList.visibility = View.GONE
            binding.noLoans.visibility = View.VISIBLE
        } else {
            binding.historyList.adapter = historyAdapter
            historyAdapter.submitList(loans)
        }
    }

    private fun launchErrorState(type: ErrorType) {
        binding.progressBar.visibility = View.GONE
        binding.historyList.visibility = View.GONE
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

    private fun launchLoadingState() {
        with(binding) {
            historyList.visibility = View.GONE
            noLoans.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}