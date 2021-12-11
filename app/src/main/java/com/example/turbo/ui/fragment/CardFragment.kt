package com.example.turbo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.data.remote.BatteryDatabase
import com.example.turbo.databinding.FragmentCardBinding
import com.example.turbo.ui.adapter.CardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CardFragment : Fragment(R.layout.fragment_card) {

    private lateinit var binding: FragmentCardBinding
    private lateinit var batteryDatabase: BatteryDatabase
    private val serviceScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCardBinding.bind(view)
        batteryDatabase = BatteryDatabase()

        var batteries = emptyList<Battery>()

        suspend fun fetchData() = withContext(Dispatchers.Main) {
            batteries = batteryDatabase.getAllBattery()

        }

        serviceScope.launch {
            fetchData()

            binding.rvCard.layoutManager = GridLayoutManager(requireContext(),2)
            binding.rvCard.adapter = CardAdapter(requireContext(),batteries)
        }



    }
}