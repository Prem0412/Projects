package com.example.moviespp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviespp.MovieViewModel
import com.example.moviespp.MoviesAdapter
import com.example.moviespp.R

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var errorTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        movieRecyclerView = view.findViewById(R.id.movieRecyclerView)
        errorTextView = view.findViewById(R.id.errorTextView)

        // Set up RecyclerView
        movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Observe movies data
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieRecyclerView.adapter = MoviesAdapter(movies)
        }

        // Observe error messages
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorTextView.text = errorMessage
        }

        // Fetch movies
        viewModel.fetchMovies()
    }
}
