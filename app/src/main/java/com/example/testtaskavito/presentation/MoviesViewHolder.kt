package com.example.testtaskavito.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskavito.R

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val poster = itemView.findViewById<ImageView>(R.id.poster)
    val card = itemView.findViewById<CardView>(R.id.card)
    val nameFilm = itemView.findViewById<TextView>(R.id.nameFilm)
    val yearFilm = itemView.findViewById<TextView>(R.id.yearFilm)
    val rating = itemView.findViewById<TextView>(R.id.rating)
}