package com.technologkal.spyderApp.ui.activity.networkTools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.R
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.databinding.ActivityNetworkMonitorConsoleBinding

class NetworkMonitorConsoleFragment : Fragment() {

    private var _binding: ActivityNetworkMonitorConsoleBinding? = null
    private val binding get() = _binding!!

    private lateinit var webView: WebView

    private fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ActivityNetworkMonitorConsoleBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //presenting webview of console within app:
        webView = binding.webview
        webView.webViewClient = WebViewClient()
        //http://<raspberry_pi_ip_address>/cacti is the pattern to follow below:
        val consoleUrl = "https://technologkal.com/"
        webView.loadUrl(consoleUrl)


        binding.launchConsoleButton.setOnClickListener {
            showToast("Launching Network monitoring console!")
            //http://<raspberry_pi_ip_address>/cacti is the pattern to follow below:
            val consoleUrl = "https://<raspberry_pi_ip_address>/cacti/"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(consoleUrl))
            startActivity(browserIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
