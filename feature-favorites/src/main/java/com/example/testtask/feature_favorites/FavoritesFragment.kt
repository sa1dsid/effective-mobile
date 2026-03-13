package com.example.testtask.feature_favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    lateinit var viewModel: FavoriteViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoritesCoursesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        recyclerView = view.findViewById(R.id.rvFavoritesCourses)

        adapter = FavoritesCoursesAdapter(
            onFavoriteClick = { course ->
                viewModel.toggleFavorite(course)
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



        lifecycleScope.launchWhenStarted {
            viewModel.favorites.collect {
                adapter.submitList(it)
            }
        }

        viewModel.loadFavorites()
    }
}
