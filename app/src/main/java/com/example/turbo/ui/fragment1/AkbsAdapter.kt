package com.example.turbo.ui.fragment1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.databinding.RowItemAkbBinding
import com.example.turbo.model.Akb
import javax.inject.Inject

class AkbsAdapter
@Inject
constructor(
    val glide: RequestManager
) : Adapter<AkbsAdapter.AkbsViewHolder> (){

    var akbs:List<Akb> = emptyList()
    set(newValue) {
        field=newValue
        notifyDataSetChanged()
    }

    class AkbsViewHolder(
        val binding: RowItemAkbBinding
    ): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkbsViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding=RowItemAkbBinding.inflate(inflater,parent,false)
        return AkbsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AkbsViewHolder, position: Int) {
        val akb = akbs[position]
        with(holder.binding){
            textViewName.text=akb.name
            textViewCategory.text=akb.category
//            chipViewCapacity.text= akb.capacity.toString()
            chipViewPrice.text=akb.price.toString()

            if(akb.photo.isNotBlank()){
                glide
//                    .with(imageViewAkb.context)
                    .load(akb.photo)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_desktop_access_disabled_24)
                    .error(R.drawable.ic_baseline_desktop_access_disabled_24)
                    .into(imageViewAkb)
            }else{
                imageViewAkb.setImageResource(R.drawable.ic_baseline_desktop_access_disabled_24)
            }

        }
    }

    override fun getItemCount(): Int = akbs.size

}