package kaan.tabcorp.ui.spacex

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kaan.tabcorp.R
import kaan.tabcorp.databinding.FragmentSpacexFlightsBinding

/**
 *  SpaceX
 */

@AndroidEntryPoint
class SpaceXFragment : Fragment() {
    private lateinit var binding: FragmentSpacexFlightsBinding

    private val viewModel: SpaceXViewModel by hiltNavGraphViewModels(R.id.spacexFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_spacex_flights, container, false)

        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.launchesList.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            viewModel.setPortraitOrientation(true)
        } else {
            binding.launchesList.layoutManager = GridLayoutManager(requireContext(), 2)

            viewModel.setPortraitOrientation(false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.filterSuccessfulLaunches.observe(viewLifecycleOwner) {
            Log.d("XXXXXX", "-----------------------------------")
            Handler(Looper.getMainLooper()).postDelayed({
                binding.launchesList.layoutManager?.scrollToPosition(0)
            }, 125)
            binding.launchesList.layoutManager?.scrollToPosition(0)

        }

        viewModel.sortClicked.observe(viewLifecycleOwner) {
            Log.d("XXXXXX", "-----------------------------------")
            Handler(Looper.getMainLooper()).postDelayed({
                binding.launchesList.layoutManager?.scrollToPosition(0)
            }, 125)
        }

        viewModel.navigate.observe(viewLifecycleOwner) {
            findNavController().navigate(SpaceXFragmentDirections.actionSpacexFragmentToLaunchFragment(it))
        }
    }
}