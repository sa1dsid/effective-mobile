package com.example.testtaskfeature_login


import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task3.LoginValidationTextWatcher
import com.example.testtaskfeature_login.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val emailAllowedFilter = InputFilter { source, _, _, _, _, _ ->
        val allowed = Regex("[a-zA-Z0-9@._+-]*")
        if (allowed.matches(source)) {
            null
        } else {
            ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.etEmail.filters = arrayOf(emailAllowedFilter)

        val textWatcher = LoginValidationTextWatcher(binding.btnLogin, binding.etEmail, binding.etPassword)

        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etPassword.addTextChangedListener(textWatcher)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_nav_home)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

