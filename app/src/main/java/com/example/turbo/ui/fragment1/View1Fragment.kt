package com.example.turbo.ui.fragment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turbo.App
import com.example.turbo.R
import com.example.turbo.databinding.FragmentView1Binding
import com.example.turbo.model.AkbsListener
import com.example.turbo.model.AkbsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class View1Fragment : Fragment(R.layout.fragment_view1) {

    private lateinit var binding: FragmentView1Binding
    @Inject
  lateinit var adapter: AkbsAdapter

    private val akbsService: AkbsService
        get() = (activity?.applicationContext as App).akbsService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentView1Binding.bind(view)
//        adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext())
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