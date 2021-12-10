package com.example.turbo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.turbo.App
import com.example.turbo.R
import com.example.turbo.databinding.FragmentView1Binding
import com.example.turbo.model.AkbsListener
import com.example.turbo.model.AkbsService
import com.example.turbo.ui.adapter.AdapterCard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class View1Fragment : Fragment(R.layout.fragment_view1) {

    private lateinit var binding: FragmentView1Binding
    @Inject
  lateinit var adapter: AdapterCard

    private val akbsService: AkbsService
        get() = (activity?.applicationContext as App).akbsService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentView1Binding.bind(view)
//        adapter = adapter

        val layoutManager = GridLayoutManager(requireContext(),2)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

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