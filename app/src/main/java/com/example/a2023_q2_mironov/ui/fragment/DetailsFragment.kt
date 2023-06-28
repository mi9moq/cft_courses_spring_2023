package com.example.a2023_q2_mironov.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentDetailsBinding
import com.example.a2023_q2_mironov.domain.entity.Loan
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.CONNECTION
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.UNAUTHORIZED
import com.example.a2023_q2_mironov.domain.entity.LoanErrorType.UNKNOWN
import com.example.a2023_q2_mironov.domain.entity.LoanStatus
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.details.DetailsState
import com.example.a2023_q2_mironov.presentation.details.DetailsState.*
import com.example.a2023_q2_mironov.presentation.details.DetailsViewModel
import com.example.a2023_q2_mironov.ui.activity.MainActivity
import com.example.a2023_q2_mironov.ui.util.colorStatus
import com.example.a2023_q2_mironov.ui.util.formatAmount
import com.example.a2023_q2_mironov.ui.util.formatDate
import com.example.a2023_q2_mironov.ui.util.formatLoanStatus
import com.example.a2023_q2_mironov.ui.util.formatPercent
import com.example.a2023_q2_mironov.ui.util.formatPeriod
import com.example.a2023_q2_mironov.ui.util.showUnauthorizedDialog
import javax.inject.Inject

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance(id: Long) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(LOAN_ID, id)
            }
        }

        private const val LOAN_ID = "loan id"

        private const val DEFAULT_ID = 0L
    }

    private var id = DEFAULT_ID

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        observeViewModel()
    }

    private fun setupClickListener() {
        binding.tryAgain.setOnClickListener {
            viewModel.loadDetails(id)
        }
    }

    private fun parseArguments() {
        val args = requireArguments()
        id = args.getLong(LOAN_ID)
        viewModel.loadDetails(id)
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::launchState)
    }

    private fun launchState(state: DetailsState) {
        when (state) {
            Initial -> Unit
            Loading -> launchLoadingState()
            is Content -> launchContentState(state.loan)
            is Error -> launchErrorState(state.type)
        }
    }

    private fun launchContentState(loan: Loan) {
        with(binding) {
            progressBar.visibility = View.GONE
            errorContainer.visibility = View.GONE
            contentContainer.visibility = View.VISIBLE
            date.text = formatDate(loan.date)
            amount.text = formatAmount(requireContext(), loan.amount)
            name.text = loan.firstName
            status.text = formatLoanStatus(requireContext(), loan.status)
            status.setTextColor(colorStatus(requireContext(), loan.status))
            surname.text = loan.lastName
            phoneNumber.text = loan.phoneNumber
            percent.text = formatPercent(loan.percent)
            period.text = formatPeriod(requireContext(), loan.period)
            when (loan.status) {
                LoanStatus.APPROVED -> {
                    getMoney.visibility = View.VISIBLE
                    getLoan()
                }
                else -> getMoney.visibility = View.GONE
            }
        }
    }

    private fun getLoan() {
        binding.findBank.setOnClickListener {
            openMaps()
        }
    }

    private fun openMaps() {
        val city = getString(R.string.novosibirsk)
        val bank = getString(R.string.sberbank)
        val uri = Uri.parse("geo:0,0?q=$bank $city")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    private fun launchLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.errorContainer.visibility = View.GONE
        binding.contentContainer.visibility = View.GONE
        binding.getMoney.visibility = View.GONE
    }

    private fun launchErrorState(type: LoanErrorType) {
        with(binding) {
            progressBar.visibility = View.GONE
            contentContainer.visibility = View.GONE
            errorContainer.visibility = View.VISIBLE
            getMoney.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}