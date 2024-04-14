package com.example.testtaskavito.presentation.secondScreen

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testtaskavito.R

class PostersViewHolder(view: View) : ViewHolder(view) {
    val poster = view.findViewById<ImageView>(R.id.posters)
}