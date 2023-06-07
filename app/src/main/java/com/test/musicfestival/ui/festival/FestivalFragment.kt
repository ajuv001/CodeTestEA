package com.test.musicfestival.ui.festival

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.musicfestival.databinding.FragmentHomeBinding
import com.test.musicfestival.ui.festival.adapter.RecordLabelAdapter
import com.test.musicfestival.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FestivalFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<FestivalViewModel>()
    private val adapter: RecordLabelAdapter by lazy { RecordLabelAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.musicFestivalRecyclerView.adapter = adapter
        binding.button.setOnClickListener {
            viewModel.getFestivalData()
        }
        observeViewModel()

        viewModel.getFestivalData()
    }

    private fun observeViewModel(){
        viewModel.festivalData.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    it.data?.let { it1 -> adapter.setData(it1) }
                }
                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    it.message.let { message ->
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }



}
