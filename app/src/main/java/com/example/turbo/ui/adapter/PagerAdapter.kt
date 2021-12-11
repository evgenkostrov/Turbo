package com.example.turbo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.databinding.RowItemPagerBinding
import com.example.turbo.databinding.RowProductBinding
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class PagerAdapter (context: Context,
                    private val batteries: List<Battery>)
    : RecyclerView.Adapter<PagerAdapter.PagerViewHolder>() {

    var glide: RequestManager

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideEP{
        fun getGlide(): RequestManager
    }
    init {
        val glideEntryPoint= EntryPointAccessors.fromApplication(context,GlideEP::class.java)
        glide=glideEntryPoint.getGlide()
    }

    inner class PagerViewHolder(
        val binding: RowItemPagerBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowItemPagerBinding.inflate(inflater, parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val battery = batteries[position]
        with(holder.binding) {

            if (battery.imageUrl.isNotBlank()) {
                glide
                    .load(battery.imageUrl)
                    .centerCrop()
                    .into(image)
            } else {
                image.setImageResource(R.drawable.ic_baseline_desktop_access_disabled_24)
            }
            name.text = battery.title
            capacity.text=battery.capacity
            current.text=battery.current
            weight.text=battery.weight
            polarity.text=battery.polarity
            description.text=battery.description



        }
    }
    override fun getItemCount(): Int = batteries.size
}