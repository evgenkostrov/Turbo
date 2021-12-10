package com.example.turbo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.turbo.databinding.FragmentView3Binding
import com.example.turbo.model.AkbsListener
import com.example.turbo.model.AkbsService
import com.example.turbo.ui.adapter.AdapterCard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class View3Fragment : Fragment(R.layout.fragment_view3) {

    private lateinit var binding: FragmentView3Binding
    @Inject
    lateinit var adapter: AdapterCard

    private val akbsService: AkbsService
        get() = (activity?.applicationContext as App).akbsService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentView3Binding.bind(view)
        val layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView2.layoutManager = layoutManager
        binding.recyclerView2.adapter = adapter

        akbsService.addListener(akbsListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        akbsService.removeListener(akbsListener)
    }

    private val akbsListener: AkbsListener = {
        adapter.akbs = it
    }
}