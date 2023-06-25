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
import com.example.a2023_q2_mironov.databinding.FragmentLoginBinding
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.ErrorType.*
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.login.Error
import com.example.a2023_q2_mironov.presentation.login.Initial
import com.example.a2023_q2_mironov.presentation.login.Loading
import com.example.a2023_q2_mironov.presentation.login.LoginState
import com.example.a2023_q2_mironov.presentation.login.LoginViewModel
import com.example.a2023_q2_mironov.ui.activity.MainActivity
import com.example.a2023_q2_mironov.util.addTextWatcher
import javax.inject.Inject

class LoginFragment : Fragment() {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    private val component by lazy {
        (requireActivity() as MainActivity).component
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
            val message = if (invalidValue) {
                getString(R.string.empty_field)
            } else {
                null
            }
            binding.tilLogin.error = message
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) { invalidValue ->
            val message = if (invalidValue) {
                getString(R.string.empty_field)
            } else {
                null
            }
            binding.tilPassword.error = message
        }
        viewModel.state.observe(viewLifecycleOwner, ::launchState)
    }

    private fun launchState(state: LoginState) {
        when (state) {
            Initial -> Unit
            is Error -> launchErrorState(state.type)
            Loading -> launchLoadingState()
        }
    }

    private fun launchErrorState(type: ErrorType) {
        binding.progressBar.visibility = View.GONE
        binding.content.visibility = View.VISIBLE
        when (type) {
            UNAUTHORIZED -> Unit
            NOT_FOUND -> binding.tilPassword.error = getString(R.string.wrong_log_or_pas)


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

    private fun launchLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.GONE
    }

    private fun setupClickListener() {
        binding.login.setOnClickListener {
            viewModel.login(
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