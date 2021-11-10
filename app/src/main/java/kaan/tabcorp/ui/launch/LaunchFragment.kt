package kaan.tabcorp.ui.launch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import kaan.tabcorp.R
import kaan.tabcorp.databinding.FragmentLaunchBinding
import kaan.tabcorp.ui.spacex.LaunchItem

@AndroidEntryPoint
class LaunchFragment : Fragment() {
    private lateinit var binding: FragmentLaunchBinding

    private val viewModel: LaunchViewModel by hiltNavGraphViewModels(R.id.launchFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_launch, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val argLaunchItem = requireArguments().getSerializable("launchItem") as LaunchItem

        viewModel.queryRocketDetails(argLaunchItem.rocketId)
        viewModel.queryLaunchDetails(argLaunchItem.flightNumber.toString())

        viewModel.rocketDetails.observe(viewLifecycleOwner) {
            binding.rocketHeadline.text = it.name
        }

        viewModel.launchDetails.observe(viewLifecycleOwner) {
            binding.launchDetails.text = it?.details.orEmpty()
            // XXX   binding. --------------- you're here
        }
    }
}