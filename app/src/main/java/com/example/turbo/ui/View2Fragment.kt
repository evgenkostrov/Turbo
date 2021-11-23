package com.example.turbo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.example.turbo.R
import com.example.turbo.data.entities.Battery
import com.example.turbo.data.remote.BatteryDatabase
import com.example.turbo.databinding.FragmentView2Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class View2Fragment : Fragment(R.layout.fragment_view2) {

    private lateinit var batteryDatabase: BatteryDatabase
    private val serviceScope = CoroutineScope(Dispatchers.Main)
    private lateinit var binding:FragmentView2Binding
@Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentView2Binding.bind(view)
        batteryDatabase= BatteryDatabase()

        var list= emptyList<Battery>()

        suspend fun fetchData() = withContext(Dispatchers.Main) {
            list = batteryDatabase.getAllBattery()
            binding.tv2.text=list[0].title
            glide
//                    .with(imageViewAkb.context)
                .load(list[0].imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_desktop_access_disabled_24)
                .error(R.drawable.ic_baseline_desktop_access_disabled_24)
                .into(binding.iv2)
        }

        serviceScope.launch {
            fetchData()
        }
        var count = 0



        binding.bat.setOnClickListener {
            count++
            binding.tv2.text=list[count].title
            glide
//                    .with(imageViewAkb.context)
                .load(list[count].imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_desktop_access_disabled_24)
                .error(R.drawable.ic_baseline_desktop_access_disabled_24)
                .into(binding.iv2)

        }







    }
}