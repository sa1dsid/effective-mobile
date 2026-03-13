package com.example.testtask.feature_home

import CoursesAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.feature_home.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvCourses)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        val adapter = CoursesAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launchWhenStarted {
            viewModel.courses.collect {
                adapter.submitList(it)
            }
        }

        viewModel.loadCourses()
    }
}
