package com.example.testtaskavito.presentation.secondScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.testtaskavito.R
import com.example.testtaskavito.domain.Poster
import com.squareup.picasso.Picasso

class PostersAdapter(private val heightImage: Int, private val countPosters: Int) : ListAdapter<Poster, PostersViewHolder>(PosterDiffItemCallback()) {


    override fun getItemCount(): Int {
        return Integer.MAX_VALUE

    }

    override fun getItem(position: Int): Poster {
        val realPosition = position % countPosters
        Log.e("PositionAdapter", "realPos $realPosition")
        Log.e("PositionAdapter", "position $position")
        return super.getItem(realPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.posters_item, parent, false)
        return PostersViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostersViewHolder, position: Int) {
        val item = getItem(position)

        Picasso.get()
            .load(item.poster)
            .placeholder(R.drawable.no_data)
            .error(R.drawable.no_data)
            .resize(0, heightImage )
            .into(holder.poster)

    }


}


class PosterDiffItemCallback : DiffUtil.ItemCallback<Poster>() {

    override fun areItemsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Poster,
        newItem: Poster
    ): Boolean {
        return oldItem == newItem
    }
}