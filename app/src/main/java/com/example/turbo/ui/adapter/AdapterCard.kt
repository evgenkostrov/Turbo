package com.example.turbo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.databinding.ItemPokemonBinding
import com.example.turbo.databinding.RowItemAkbBinding
import com.example.turbo.model.Akb
import javax.inject.Inject


class AdapterCard
@Inject
constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<AdapterCard.AkbViewHolder>() {

    var akbs: List<Akb> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class AkbViewHolder(
        val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkbViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(inflater, parent, false)
        return AkbViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AkbViewHolder, position: Int) {
        val akb = akbs[position]
        with(holder.binding) {
            name.text = akb.name
//                textViewCategory.text=akb.category
////            chipViewCapacity.text= akb.capacity.toString()
//                chipViewPrice.text=akb.price.toString()

            if (akb.photo.isNotBlank()) {
                glide
//                    .with(imageViewAkb.context)
                    .load(akb.photo)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_desktop_access_disabled_24)
                    .error(R.drawable.ic_baseline_desktop_access_disabled_24)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.ic_baseline_desktop_access_disabled_24)
            }
        }
    }
    override fun getItemCount(): Int = akbs.size
}