package com.test.musicfestival.ui.festival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.test.musicfestival.data.model.Festival
import com.test.musicfestival.databinding.FestivalItemLayoutBinding

class FestivalsAdapter(private val festivals: List<Festival?>): Adapter<FestivalsAdapter.FestivalViewHolder> () {

    inner class FestivalViewHolder(private val itemView: View, val binding: FestivalItemLayoutBinding) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalViewHolder {
        val binding = FestivalItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FestivalViewHolder(binding.root, binding)
    }

    override fun getItemCount() = festivals.size

    override fun onBindViewHolder(holder: FestivalViewHolder, position: Int) {

        holder.binding.festival = festivals[position]
        holder.binding.executePendingBindings()
    }
}