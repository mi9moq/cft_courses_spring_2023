package com.example.a2023_q2_mironov.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentGuidBinding
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.guid.GuidState
import com.example.a2023_q2_mironov.presentation.guid.GuidState.Confirm
import com.example.a2023_q2_mironov.presentation.guid.GuidState.Fill
import com.example.a2023_q2_mironov.presentation.guid.GuidState.Finish
import com.example.a2023_q2_mironov.presentation.guid.GuidState.Start
import com.example.a2023_q2_mironov.presentation.guid.GuidViewModel
import javax.inject.Inject

class GuidFragment : Fragment() {

    companion object {
        fun newInstance() = GuidFragment()
    }

    private val component by lazy {
        (requireActivity() as MainActivity).component
    }

    private var _binding: FragmentGuidBinding? = null
    private val binding: FragmentGuidBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GuidViewModel::class.java]
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
        _binding = FragmentGuidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.next.setOnClickListener {
            viewModel.showNext()
        }
        binding.previous.setOnClickListener {
            viewModel.showPrevious()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::launchState)
    }

    private fun launchState(state: GuidState) {
        when (state) {
            Start -> launchStartState()
            Fill -> launchFillState()
            Confirm -> launchConfirmState()
            Finish -> launchFinishConfirm()
        }
    }

    private fun launchStartState() {
        with(binding) {
            icon.setImageResource(R.drawable.ic_guid_start)
            description.text = getString(R.string.guid_start)
        }
    }

    private fun launchFillState() {
        with(binding) {
            icon.setImageResource(R.drawable.ic_guid_fill)
            description.text = getString(R.string.guid_fill)
        }
    }

    private fun launchConfirmState() {
        with(binding) {
            icon.setImageResource(R.drawable.ic_guid_complete)
            description.text = getString(R.string.guid_confirm)
        }
    }

    private fun launchFinishConfirm() {
        with(binding) {
            icon.setImageResource(R.drawable.ic_guid_loan_approved)
            description.text = getString(R.string.guid_finish)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}