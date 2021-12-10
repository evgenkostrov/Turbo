package com.example.turbo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.databinding.RowCategoryBinding
import com.example.turbo.model.Category
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class CategoryAdapter (context: Context,
                       private val batteries: List<Battery>,
                       private val categories: List<Category>)
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(
        val binding: RowCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        with(holder.binding) {
            tvCategory.text = category.categoryName1
            tvAllFavorite.text = category.categoryName2
            imageView.setImageResource(category.categoryDrawable)
            rvProduct.adapter=ProductAdapter(root.context,batteries)
        }
    }
    override fun getItemCount(): Int = categories.size
}