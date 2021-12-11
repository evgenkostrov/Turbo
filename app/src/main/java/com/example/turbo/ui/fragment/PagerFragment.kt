package com.example.turbo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.data.remote.BatteryDatabase
import com.example.turbo.databinding.FragmentPagerBinding
import com.example.turbo.ui.adapter.CardAdapter
import com.example.turbo.ui.adapter.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PagerFragment : Fragment(R.layout.fragment_pager) {

    private lateinit var binding:FragmentPagerBinding
    private lateinit var batteryDatabase: BatteryDatabase
    private val serviceScope = CoroutineScope(Dispatchers.Main)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPagerBinding.bind(view)
        batteryDatabase = BatteryDatabase()

        var batteries = emptyList<Battery>()

        suspend fun fetchData() = withContext(Dispatchers.Main) {
            batteries = batteryDatabase.getAllBattery()

        }

        serviceScope.launch {
            fetchData()
            binding.rvPager.adapter = PagerAdapter(requireContext(),batteries)

        }





    }
}