package com.technologkal.spyderApp.ui.activity.networkTools

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.R
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.inter.ISpeedTestListener
import fr.bmartel.speedtest.model.SpeedTestError

class NetworkSpeedTestActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var tvSpeed: TextView
    private lateinit var btnStart: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_speed_test)
        tvStatus = findViewById(R.id.tv_status)
        tvSpeed = findViewById(R.id.tv_speed)
        btnStart = findViewById(R.id.btn_start)
        progressBar = findViewById(R.id.progressBar)
        webView = findViewById(R.id.webview)

        btnStart.setOnClickListener {
            it.isEnabled = false
            tvStatus.text = "Status: Testing..."
            SpeedTestTask().execute()
        }


        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Hide the progress bar when the page finishes loading
                progressBar.visibility = View.GONE
            }
        }
        val htmlContent = """
        <html>
            <body>
                <!--OST Widget code start-->
                    <div style="text-align:right;">
                        <div style="min-height:360px;">
                            <div style="width:100%;height:0;padding-bottom:50%;position:relative;">
                                <iframe style="border:none;position:absolute;top:0;left:0;width:100%;height:100%;min-height:360px;border:none;overflow:hidden !important;" src="https://openspeedtest.com/speedtest?Run=2">
                                </iframe>
                            </div>
                        </div>Provided by <a href="https://openspeedtest.com">OpenSpeedtest.com</a>
                    </div>
                <!-- OST Widget code end -->
            </body>
        </html>
        """

        webView.loadData(htmlContent, "text/html", "UTF-8")
    }

    inner class SpeedTestTask : AsyncTask<Void, String, String>() {

        //Redundancy applied to iterate through multiple test servers:
        private val testServerUrls = listOf(
            "http://ipv4.ikoula.testdebit.info/1M.iso",
            "http://speedtest.ftp.otenet.gr/files/test1Mb.db",
            "http://speedtest.tele2.net/100MB.zip",
            "http://speedtest.mytelecoms.net/files/100Mb.dat",
            "http://speedtest.ftp.otenet.gr/files/test100k.db",
            "http://speedtest.ftp.otenet.gr/files/test10Mb.db"
        )

        override fun doInBackground(vararg params: Void?): String {
            var speed = ""

            for (url in testServerUrls) {
                val speedTestSocket = SpeedTestSocket()
                speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {
                    override fun onCompletion(report: SpeedTestReport?) {
                        report?.transferRateBit?.let {
                            // Calculate speed in Mbps
                            val speedMbps = it.toDouble() / 1000000
                            speed = String.format("%.2f Mbps", speedMbps)
                            Log.d("SpeedTest", "Speed: $speed")
                            publishProgress(speed)
                        }
                    }

                    override fun onError(speedTestError: SpeedTestError?, errorMessage: String?) {
                        Log.e("SpeedTest", "Error: $errorMessage")
                    }

                    override fun onProgress(percent: Float, report: SpeedTestReport?) {
                        Log.d("SpeedTest", "Progress: $percent%")
                    }
                })

                speedTestSocket.startFixedDownload(url, 10000) // Download 10MB file
            }

            return speed
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            tvStatus.text = "Status: Done"
            tvSpeed.text = values[0]
            btnStart.isEnabled = true
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // Do something with the result if needed
        }
    }
}
