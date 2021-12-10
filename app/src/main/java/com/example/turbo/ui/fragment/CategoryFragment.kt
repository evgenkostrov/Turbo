package com.example.turbo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.data.remote.BatteryDatabase
import com.example.turbo.databinding.FragmentCategoryBinding
import com.example.turbo.model.Category
import com.example.turbo.ui.adapter.CategoryAdapter
import com.example.turbo.ui.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {


    private lateinit var binding: FragmentCategoryBinding
    private lateinit var batteryDatabase: BatteryDatabase
    private val serviceScope = CoroutineScope(Dispatchers.Main)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCategoryBinding.bind(view)
        batteryDatabase = BatteryDatabase()

        var batteries = emptyList<Battery>()
        var categories = mutableListOf<Category>()


        val categoryName1 = resources.getStringArray(R.array.category1)
        val categoryName2 = resources.getStringArray(R.array.category2)
        val categoryDrawable = resources.obtainTypedArray(R.array.category_drawable)
        for(i in categoryName1.indices){
            val category = Category(categoryName1[i],
            categoryName2[i],
            categoryDrawable.getResourceId(i,-1))
            categories.add(category)
        }
       categoryDrawable.recycle()




        suspend fun fetchData() = withContext(Dispatchers.Main) {
            batteries = batteryDatabase.getAllBattery()

        }

        serviceScope.launch {
            fetchData()

            binding.rvCategory.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCategory.adapter = CategoryAdapter(requireActivity(), batteries,categories)

        }



    }
}