package com.example.turbo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.databinding.RowItemCardBinding
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent


class CardAdapter(context: Context,
                  private val batteries: List<Battery>)
    : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var glide: RequestManager

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideEP{
        fun getGlide():RequestManager
    }
    init {
        val glideEntryPoint= EntryPointAccessors.fromApplication(context,GlideEP::class.java)
        glide=glideEntryPoint.getGlide()
    }

    inner class CardViewHolder(
        val binding: RowItemCardBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowItemCardBinding.inflate(inflater, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val battery = batteries[position]
        with(holder.binding) {
            name.text = battery.title
            if (battery.imageUrl.isNotBlank()) {
                glide
                    .load(battery.imageUrl)
                    .centerCrop()
                    .into(image)
            } else {
                image.setImageResource(R.drawable.ic_baseline_desktop_access_disabled_24)
            }
        }
    }
    override fun getItemCount(): Int = batteries.size
}