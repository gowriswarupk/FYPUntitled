package com.technologkal.spyderApp.ui.activity.networkTools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.databinding.ActivityNetworkMonitorConsoleBinding

class NetworkMonitorConsoleActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNetworkMonitorConsoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}
