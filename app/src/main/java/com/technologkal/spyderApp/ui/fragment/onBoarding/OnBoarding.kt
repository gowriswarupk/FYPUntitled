package com.technologkal.spyderApp.ui.fragment.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.technologkal.spyderApp.adapter.SliderAdapter
import com.technologkal.spyderApp.utils.Prefs
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.databinding.FragmentOnBoardingBinding

class OnBoarding : Fragment() {
    private lateinit var viewPager2: ViewPager2
    private var sliderAdapter: SliderAdapter = SliderAdapter()
    private lateinit var viewModel: OnBoardingViewModel
    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(OnBoardingViewModel::class.java)
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.onBoardingViewModel = viewModel
        binding.lifecycleOwner = this

        viewPager2 = binding.viewPager2

        viewModel.dataSet.observe(viewLifecycleOwner, Observer {
            viewPager2.adapter = sliderAdapter
            sliderAdapter.setItems(it)
            TabLayoutMediator(
                binding.indicator,
                viewPager2,
                object : TabLayoutMediator.TabConfigurationStrategy {
                    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                        viewPager2.setCurrentItem(tab.position, true)
                    }
                }).attach()
        })
        viewModel.startNavigation.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController()
                    .navigate(OnBoardingDirections.actionOnBoardingToAuthFragment())
                Prefs.getInstance(requireContext())!!.hasCompletedWalkthrough = false
                viewModel.doneNavigation()
            }
        })
        binding.viewPager2.registerOnPageChangeCallback(viewModel.pagerCallBack)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::viewPager2.isInitialized) {
            viewPager2.unregisterOnPageChangeCallback(viewModel.pagerCallBack)
        }
    }
}
