package com.example.testtaskavito.presentation.firstScreen

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.testtaskavito.R
import com.example.testtaskavito.data.local.ModelForListLocal
import com.squareup.picasso.Picasso

class MoviesAdapter(
    displayMetrics: DisplayMetrics,
    private val dimenCornerRatingTV: Float
) : PagingDataAdapter<ModelForListLocal, MoviesViewHolder>(MovieDiffItemCallback) {

    val heightImage = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        300f,
        displayMetrics
    )

    var onClickListenerItem: ((ModelForListLocal) -> Unit)? = null

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = getItem(position)
        Log.e("onBindViewHolder", movieItem.toString())
        val poster = movieItem?.poster
        val rating = movieItem?.rating
        if (rating != null) {
            holder.rating.text = rating.kp.toString()

            val newBackground = when (rating.kp?:5f) {
                in 8f..10f -> {
                    createBackgroundTextView(Color.GREEN, dimenCornerRatingTV)
                }

                in 5f..8f -> {
                    createBackgroundTextView(Color.YELLOW, dimenCornerRatingTV)
                }

                else -> {
                    createBackgroundTextView(Color.RED, dimenCornerRatingTV)
                }
            }
            holder.rating.background = newBackground

        } else {
            holder.rating.visibility = View.GONE
        }

        Picasso.get()
            .load(poster)
            .error(R.drawable.default_poster)
            .resize(holder.poster.width, heightImage.toInt())
            .into(holder.poster)

        val year = if (movieItem?.year != null) {
            movieItem.year.toString()
        } else {
            ""
        }
        holder.nameFilm.text = movieItem?.name ?: R.string.defaultString.toString()
        holder.yearFilm.text = year

        holder.card.setOnClickListener {
            movieItem?.let {
                onClickListenerItem?.invoke(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movies_list_item_model, parent, false)

        return MoviesViewHolder(itemView)
    }

    private fun createBackgroundTextView(color: Int, cornerRadius: Float): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            this.cornerRadius = cornerRadius
            setColor(color)

        }

    }

}
//Почему работает только объектом
object MovieDiffItemCallback : DiffUtil.ItemCallback<ModelForListLocal>() {

    override fun areItemsTheSame(oldItem: ModelForListLocal, newItem: ModelForListLocal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ModelForListLocal,
        newItem: ModelForListLocal
    ): Boolean {
        return oldItem == newItem
    }
}