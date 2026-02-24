package com.example.testtaskfeature_login


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.core.net.toUri
import com.example.testtaskfeature_login.databinding.FragmentLoginBinding
import com.example.testtaskfeature_login.navigation.LoginNavigator

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val navigator: LoginNavigator by lazy {
        requireActivity() as LoginNavigator
    }

    private fun onLoginClicked() {
        navigator.openHome()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            onLoginClicked()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

