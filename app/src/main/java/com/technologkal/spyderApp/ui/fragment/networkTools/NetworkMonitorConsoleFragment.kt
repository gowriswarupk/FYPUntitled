package com.technologkal.spyderApp.ui.fragment.networkTools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.databinding.FragmentNetworkMonitorConsoleBinding

class NetworkMonitorConsoleFragment : Fragment() {

    private var _binding: FragmentNetworkMonitorConsoleBinding? = null
    private val binding get() = _binding!!

    private fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNetworkMonitorConsoleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.launchConsoleButton.setOnClickListener {
            showToast("Launching Network monitoring console!")
            //http://<raspberry_pi_ip_address>/cacti is the pattern to follow below:
            val consoleUrl = "http://<raspberry_pi_ip_address>/cacti/"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(consoleUrl))
            startActivity(browserIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
