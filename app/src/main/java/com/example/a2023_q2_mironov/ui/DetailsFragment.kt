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
import com.example.a2023_q2_mironov.databinding.FragmentDetailsBinding
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.details.DetailsViewModel
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.details.DetailsState
import com.example.a2023_q2_mironov.util.formatDate
import com.example.a2023_q2_mironov.util.formatLoanStatus
import javax.inject.Inject

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance(id: Long) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(LOAN_ID, id)
            }
        }

        private const val LOAN_ID = "loan id"
    }

    private val component by lazy {
        (requireActivity() as MainActivity).component
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
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
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArguments()
        observeViewModel()
    }

    private fun parseArguments() {
        val args = requireArguments()
        val id = args.getLong(LOAN_ID)
        viewModel.loadDetails(id)
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailsState.Initial -> Unit
                DetailsState.Loading -> launchLoadingState()
                is DetailsState.Content -> launchContentState(state.loan)
                is DetailsState.Error -> launchErrorState(state.type)
            }
        }
    }

    private fun launchContentState(loan: Loan) {
        with(binding) {
            progressBar.visibility = View.GONE
            container.visibility = View.VISIBLE
            date.text = formatDate(loan.date)
            amount.text = loan.amount.toString()
            name.text = loan.firstName
            status.text = formatLoanStatus(requireContext(), loan.status)
            surname.text = loan.lastName
            phoneNumber.text = loan.phoneNumber
            percent.text = loan.percent.toString()
            period.text = loan.period.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}