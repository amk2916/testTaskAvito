package com.example.testtaskavito.presentation.secondScreen

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testtaskavito.R

class ActorViewHolder(view: View) : ViewHolder(view) {
    val card = view.findViewById<CardView>(R.id.actorsCard)
    val photo = view.findViewById<ImageView>(R.id.actorsPhoto)
    val actorsName = view.findViewById<TextView>(R.id.nameActors)
}