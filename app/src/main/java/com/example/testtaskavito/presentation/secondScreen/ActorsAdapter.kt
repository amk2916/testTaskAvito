package com.example.testtaskavito.presentation.secondScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testtaskavito.R
import com.example.testtaskavito.data.local.ModelForListLocal
import com.example.testtaskavito.domain.Actor
import com.squareup.picasso.Picasso

class ActorsAdapter : PagingDataAdapter<Actor, ActorViewHolder>(ActorDiffItemCallback) {


    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = getItem(position)

        Log.e("dslk", item.toString())

        Picasso
            .get()
            .load(item?.photo)
            .placeholder(R.drawable.no_data)
            .error(R.drawable.no_data)
            .into(holder.photo)

        holder.actorsName.text = item?.name ?: "нет данных"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(
            R.layout.actors_item,
            parent,
            false
        )

        return ActorViewHolder(view)
    }
}


//Одинаковые объекты
object ActorDiffItemCallback : DiffUtil.ItemCallback<Actor>() {

    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Actor,
        newItem: Actor
    ): Boolean {
        return oldItem == newItem
    }
}

