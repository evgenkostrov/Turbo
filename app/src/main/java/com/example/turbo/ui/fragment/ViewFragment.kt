package com.example.turbo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.turbo.App
import com.example.turbo.R
import com.example.turbo.databinding.FragmentViewBinding
import com.example.turbo.model.AkbsListener
import com.example.turbo.model.AkbsService
import com.example.turbo.ui.adapter.AkbsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewFragment : Fragment(R.layout.fragment_view) {

    private lateinit var binding: FragmentViewBinding
    @Inject
  lateinit var adapter: AkbsAdapter

    private val akbsService: AkbsService
        get() = (activity?.applicationContext as App).akbsService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentViewBinding.bind(view)
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