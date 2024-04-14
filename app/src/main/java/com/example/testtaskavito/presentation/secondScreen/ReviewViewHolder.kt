package com.example.testtaskavito.presentation.secondScreen

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testtaskavito.R

class ReviewViewHolder(view: View) : ViewHolder(view) {
    val card = view.findViewById<CardView>(R.id.commentCard)
    val name = view.findViewById<TextView>(R.id.nameUser)
    val review = view.findViewById<TextView>(R.id.comment)
}