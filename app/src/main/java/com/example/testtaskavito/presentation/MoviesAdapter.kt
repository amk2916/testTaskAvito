package com.example.testtaskavito.presentation

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.testtaskavito.R
import com.example.testtaskavito.domain.MovieForList
import com.squareup.picasso.Picasso

class MoviesAdapter : PagingDataAdapter<MovieForList, MoviesViewHolder>(MovieDiffItemCallback) {
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = getItem(position)
        val poster = movieItem?.poster
        val rating = movieItem?.rating
        if (rating != null) {
            holder.rating.text = rating
            if(rating.toFloat() <=8f){
                val newBackground = when(rating.toFloat()){
                    in 5f..8f ->{
                        createBackgroundTextView(Color.YELLOW,holder.dimenCornerRatingTV)
                    }
                    else ->{
                        createBackgroundTextView(Color.RED,holder.dimenCornerRatingTV)
                    }
                }
                holder.rating.background = newBackground
            }
        }else{
            holder.rating.visibility = View.GONE
        }

        Picasso.get()
            .load(poster)
            .error(R.drawable.default_poster)
            .into(holder.poster)

        val year = if(movieItem?.year != null){ movieItem.year.toString() }else{""}
        holder.nameFilm.text = movieItem?.name ?: R.string.defaultString.toString()
        holder.yearFilm.text = year
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movies_item, parent, false)

        return MoviesViewHolder(itemView)
    }

    private companion object {

    }

    private fun createBackgroundTextView(color: Int, cornerRadius: Float) : GradientDrawable {
       return GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                this.cornerRadius = cornerRadius
                setColor(color)

        }

    }

}

private object MovieDiffItemCallback : DiffUtil.ItemCallback<MovieForList>() {

    override fun areItemsTheSame(oldItem: MovieForList, newItem: MovieForList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieForList, newItem: MovieForList): Boolean {
        return oldItem == newItem
    }
}