package com.test.musicfestival.ui.festival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.test.musicfestival.data.model.MusicBand
import com.test.musicfestival.databinding.BandItemLayoutBinding

class BandsAdapter(private val bands: List<MusicBand>): Adapter<BandsAdapter.BandsViewHolder>() {
    inner class BandsViewHolder(private val itemView: View, val binding: BandItemLayoutBinding) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandsViewHolder {
        val binding = BandItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BandsViewHolder(binding.root, binding)
    }

    override fun getItemCount() = bands.size

    override fun onBindViewHolder(holder: BandsViewHolder, position: Int) {

        holder.binding.band = bands[position]
        holder.binding.festivalRecycler.adapter = bands[position].festivals?.toList()
            ?.let { FestivalsAdapter(it) }
        holder.binding.executePendingBindings()
    }
}