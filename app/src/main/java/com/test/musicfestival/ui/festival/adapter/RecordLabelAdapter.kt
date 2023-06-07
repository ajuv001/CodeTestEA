package com.test.musicfestival.ui.festival.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.test.musicfestival.data.model.RecordLabel
import com.test.musicfestival.databinding.RecordLabelLayoutBinding

class RecordLabelAdapter(): Adapter<RecordLabelAdapter.RecordLabelViewHolder>() {
    inner class RecordLabelViewHolder(private val itemView: View, val binding: RecordLabelLayoutBinding) : RecyclerView.ViewHolder(itemView)

    private var recordLabels: List<RecordLabel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordLabelViewHolder {
        val binding = RecordLabelLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordLabelViewHolder(binding.root, binding)
    }

    override fun getItemCount() = recordLabels.size

    override fun onBindViewHolder(holder: RecordLabelViewHolder, position: Int) {
        holder.binding.recordLabel = recordLabels[position]
        holder.binding.bandsRecycler.adapter = recordLabels[position].musicBands?.toList()
            ?.let { BandsAdapter(it) }
        holder.binding.executePendingBindings()
    }

    fun setData(listData: List<RecordLabel>){
        recordLabels = listData
        notifyDataSetChanged()
    }
}