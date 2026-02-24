package com.example.testtask

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.testtask.feature_home.HomeFragment
import com.example.testtaskfeature_login.navigation.LoginNavigator

class LoginNavigatorImpl(private val context: Context): LoginNavigator {
    override fun openHome() {
        if (context is AppCompatActivity) {
            val activity = context
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.root, HomeFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}