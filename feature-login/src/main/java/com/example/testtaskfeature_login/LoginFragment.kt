package com.example.testtaskfeature_login


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.core.net.toUri

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val navController = findNavController()
            navController.navigate("myapp://home".toUri())
        }

    }
}

