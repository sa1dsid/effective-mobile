package com.example.testtask.feature_home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var recyclerView: RecyclerView
    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvCourses)

        val adapter = CoursesAdapter(
            onFavoriteClick = { course ->
                viewModel.toggleFavorite(course)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.courses.collect {
                adapter.submitList(it)
            }
        }

        viewModel.loadCourses()
    }
}
