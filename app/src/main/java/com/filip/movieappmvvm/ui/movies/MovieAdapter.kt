package com.filip.movieappmvvm.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filip.movieappmvvm.R
import com.filip.movieappmvvm.data.model.MovieModel
import com.filip.movieappmvvm.databinding.ItemSearchedMovieBinding

class   MovieAdapter(private val onMovieClicked: (MovieModel) -> Unit) :
    RecyclerView.Adapter<MovieHolder>() {

    private val movieList: MutableList<MovieModel> = mutableListOf()

    fun setData(movieList: MutableList<MovieModel>){
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_searched_movie, parent, false)
        val binding = ItemSearchedMovieBinding.bind(view)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindData(movieList[position], onMovieClicked)
    }

    override fun getItemCount(): Int =  movieList.size

}