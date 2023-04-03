package com.example.jumpingmind_assignment.feature.dashboard.ui

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jumpingmind_assignment.R
import com.example.jumpingmind_assignment.databinding.FragmentLandingBinding
import com.example.jumpingmind_assignment.feature.dashboard.adapters.WeatherAdapter
import com.example.jumpingmind_assignment.feature.dashboard.data.ListItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class LandingFragment : Fragment() {


    private val viewModel: LandingViewModel by viewModels()
    private lateinit var geocoder: Geocoder
    private var addresses: MutableList<Address> = mutableListOf()
    lateinit var binding: FragmentLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setAutoCompleteAdapter()
        setObserver()
        return binding.root
    }


    private fun setAutoCompleteAdapter() {
        geocoder = Geocoder(requireContext())
        // Set the adapter to the AutoCompleteTextView
        binding.autocompleteTextview
    }

    private fun searchCity() {
        binding.autocompleteTextview.apply {
            if (this.text.toString().isEmpty()) {
                return@apply
            }
            viewModel.showLoading()
            geocoder.getFromLocationName(this.text.toString(), 1)?.let {
                addresses = it
                if (it.isNotEmpty()) {
                    viewModel.fetchWeatherData(it[0].latitude, it[0].longitude)
                    viewModel.hideLoading()
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.apply {
            weather.observe(viewLifecycleOwner) {
                setAdapterToRecyclerView(it)
                showTapMessage()
            }
            action.observe(viewLifecycleOwner) {
                if (it == LandingViewModel.Action.searchCity) {
                    searchCity()
                } else if (it == LandingViewModel.Action.logout) {
                    logout()
                }
            }
        }
    }

    private fun logout() {
        findNavController().navigate(R.id.backToLogin)
    }

    private fun setAdapterToRecyclerView(list: List<ListItem>) {
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = WeatherAdapter(requireContext(), list) {
                viewModel.showSelectedItem(it)
            }
        }
    }

    private fun showTapMessage() {
        binding.tapMessageContainer.visibility = View.VISIBLE
        binding.detailContainer.visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed({
            binding.tapMessageContainer.visibility = View.GONE
            viewModel.weather.value?.let {
                if (it.isNotEmpty()) {
                    binding.detailContainer.visibility = View.VISIBLE
                    viewModel.showSelectedItem(it[0])
                }
            }
        }, 3000)
    }

}