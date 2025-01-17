package com.example.moviespp





import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviespp.MovieViewModel
import com.example.moviespp.R

// Updated MoviesAdapter without Glide
class MoviesAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Create variables and assign IDs
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        private val genresTextView: TextView = itemView.findViewById(R.id.genresTextView)
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)

        fun bind(movie: Movie) {
            // Bind data to views
            titleTextView.text = movie.title
            yearTextView.text = "Year: ${movie.year}"
            genresTextView.text = "Genres: ${movie.genres.joinToString(", ")}"

            // Replace with logic for setting placeholder or drawable
            posterImageView.setImageResource(R.drawable.ic_launcher_background) // Replace `placeholder_image` with actual drawable name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}

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

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieRecyclerView.adapter = MoviesAdapter(movies)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorTextView.text = errorMessage
        }

        viewModel.fetchMovies()
    }
}
