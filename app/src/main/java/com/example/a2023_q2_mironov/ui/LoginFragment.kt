package com.example.a2023_q2_mironov.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_mironov.R
import com.example.a2023_q2_mironov.databinding.FragmentLoginBinding
import com.example.a2023_q2_mironov.presentation.ErrorType
import com.example.a2023_q2_mironov.presentation.ViewModelFactory
import com.example.a2023_q2_mironov.presentation.login.Error
import com.example.a2023_q2_mironov.presentation.login.Initial
import com.example.a2023_q2_mironov.presentation.login.Loading
import com.example.a2023_q2_mironov.presentation.login.LoginViewModel
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
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                Initial -> Unit
                is Error -> launchErrorState(state.type)
                Loading -> launchLoadingState()
            }
        }
    }

    private fun launchErrorState(type: ErrorType) {
        binding.progressBar.visibility = View.GONE
        binding.content.visibility = View.VISIBLE
        when (type) {
            ErrorType.UNAUTHORIZED -> Unit
            ErrorType.NOT_FOUND -> {
                val message = getString(R.string.wrong_log_or_pas)
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
        binding.etLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}