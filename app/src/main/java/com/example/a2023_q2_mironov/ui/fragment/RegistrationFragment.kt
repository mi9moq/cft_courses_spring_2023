package com.example.a2023_q2_mironov.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentRegistrationBinding
import com.example.a2023_q2_mironov.domain.entity.AuthErrorType
import com.example.a2023_q2_mironov.domain.entity.AuthErrorType.*
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.registration.RegistrationState
import com.example.a2023_q2_mironov.presentation.registration.RegistrationState.*
import com.example.a2023_q2_mironov.presentation.registration.RegistrationViewModel
import com.example.a2023_q2_mironov.ui.activity.MainActivity
import com.example.a2023_q2_mironov.util.addTextWatcher
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    private val component by lazy {
        (requireActivity() as MainActivity).component
    }

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegistrationViewModel::class.java]
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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        addTextChangeListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) { invalidValue ->
            binding.tilLogin.error = emptyFieldMessage(invalidValue)
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) { invalidValue ->
            binding.tilPassword.error = emptyFieldMessage(invalidValue)
        }
        viewModel.state.observe(viewLifecycleOwner, ::launchState)
    }

    private fun emptyFieldMessage(invalid: Boolean): String? =
        if (invalid) getString(R.string.empty_field) else null

    private fun launchState(state: RegistrationState) {
        when (state) {
            Initial -> Unit
            is Error -> launchErrorState(state.type)
            Loading -> launchLoadingState()
        }
    }

    private fun launchErrorState(type: AuthErrorType) {
        binding.progressBar.visibility = View.GONE
        binding.content.visibility = View.VISIBLE
        when (type) {
            WRONG_LOGIN_OR_PASSWORD -> Unit

            UNKNOWN -> {
                val message = getString(R.string.unknown_error)
                showToast(message)
            }

            CONNECTION -> {
                val message = getString(R.string.connection_error)
                showToast(message)
            }

            USER_EXIST -> binding.tilLogin.error =
                getString(R.string.user_exists)

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun launchLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.GONE
    }

    private fun setupClickListener() {
        binding.login.setOnClickListener {
            viewModel.registration(
                binding.etLogin.text?.toString(),
                binding.etPassword.text?.toString()
            )
        }
    }

    private fun addTextChangeListener() {
        binding.etLogin.addTextChangedListener(addTextWatcher(viewModel::resetErrorInputName))
        binding.etPassword.addTextChangedListener(addTextWatcher(viewModel::resetErrorInputPassword))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}