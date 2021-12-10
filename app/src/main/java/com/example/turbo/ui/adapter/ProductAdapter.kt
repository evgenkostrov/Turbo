package com.example.turbo.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.databinding.RowProductBinding
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent


class ProductAdapter(context: Context,
                     private val batteries: List<Battery>)
: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    lateinit var glide: RequestManager

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideEP{
        fun getGlide():RequestManager
    }
    init {
        val glideEntryPoint=EntryPointAccessors.fromApplication(context,GlideEP::class.java)
        glide=glideEntryPoint.getGlide()
    }

     inner class ProductViewHolder(
        val binding: RowProductBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val battery = batteries[position]
        with(holder.binding) {
            tvProduct.text = battery.title
            if (battery.imageUrl.isNotBlank()) {
                glide
                    .load(battery.imageUrl)
                    .centerCrop()
                    .into(ivProduct)
            } else {
                ivProduct.setImageResource(R.drawable.ic_baseline_desktop_access_disabled_24)
            }
        }
    }
    override fun getItemCount(): Int = batteries.size
}