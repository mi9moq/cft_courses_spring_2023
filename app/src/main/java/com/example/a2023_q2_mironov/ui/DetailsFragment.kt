package com.example.a2023_q2_mironov.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.databinding.FragmentDetailsBinding
import com.example.a2023_q2_mironov.presentation.details.DetailsViewModel
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
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
    }

    private fun parseArguments(){
        val args = requireArguments()
        val id = args.getLong(LOAN_ID)
        viewModel.loadDetails(id)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}