package kaan.tabcorp.ui.news

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kaan.tabcorp.R
import kaan.tabcorp.databinding.FragmentSpacexFlightsBinding

/**
 *  SpaceX grid/recyclerview [Fragment] subclass as the default destination in the navigation.
 */
open class SpaceXFragment : Fragment() {

    private lateinit var binding: FragmentSpacexFlightsBinding

    private val viewModel: SpaceXViewModel by hiltNavGraphViewModels(R.id.spacexFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpacexFlightsBinding.inflate(inflater, container, false)

        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.launchesList.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            viewModel.setPortraitOrientation(true)

            PagerSnapHelper().attachToRecyclerView(binding.launchesList)
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
    }
}