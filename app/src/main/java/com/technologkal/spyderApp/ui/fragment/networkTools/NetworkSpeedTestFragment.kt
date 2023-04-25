package com.technologkal.spyderApp.ui.fragment.networkTools

import android.content.ContentValues.TAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.R

import android.os.AsyncTask
import android.util.Log
import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.SpeedTestSocket
import fr.bmartel.speedtest.inter.ISpeedTestListener
import fr.bmartel.speedtest.model.SpeedTestError

class NetworkSpeedTestFragment : Fragment() {

    private lateinit var tvStatus: TextView
    private lateinit var tvSpeed: TextView
    private lateinit var btnStart: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network_speed_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvStatus = view.findViewById(R.id.tv_status)
        tvSpeed = view.findViewById(R.id.tv_speed)
        btnStart = view.findViewById(R.id.btn_start)
        progressBar = view.findViewById(R.id.progressBar)

        btnStart.setOnClickListener {
            it.isEnabled = false
            tvStatus.text = "Status: Testing..."
            SpeedTestTask().execute()
        }
    }

    inner class SpeedTestTask : AsyncTask<Void, String, String>() {

        //Redundency applied to iterate through multiple test servers:
        private val testServerUrls = listOf(
            "http://ipv4.ikoula.testdebit.info/1M.iso",
            "http://speedtest.ftp.otenet.gr/files/test1Mb.db",
            "http://speedtest.tele2.net/100MB.zip",
            "http://speedtest.mytelecoms.net/files/100Mb.dat",
            "http://speedtest.ftp.otenet.gr/files/test100k.db",
            "http://speedtest.ftp.otenet.gr/files/test500k.db",
            "http://speedtest.ftp.otenet.gr/files/test1000k.db",
            "http://speedtest.blizoo.mk/speedtest/upload.php",
            "http://ookla1.exetel.com.au/100MB.test",
            "http://ookla2.exetel.com.au/100MB.test",
            "http://mirror.internode.on.net/pub/test/100meg.test",
            "http://speedtest.dal01.softlayer.com/downloads/test100.zip"
        )


        override fun doInBackground(vararg params: Void?): String? {
            val speedTestSocket = SpeedTestSocket()

            speedTestSocket.addSpeedTestListener(object : ISpeedTestListener {
                override fun onCompletion(report: SpeedTestReport) {
                    publishProgress(
                        "Status: Completed",
                        "%.2f Mbps".format(report.transferRateBit.toDouble() / 1_000_000),
                        "100"
                    )
                }

                override fun onError(speedTestError: SpeedTestError, errorMessage: String) {
                    publishProgress("Status: Error", "Speed: 0 Mbps", "0")
                }

                override fun onProgress(percent: Float, report: SpeedTestReport) {
                    publishProgress(
                        "Status: Testing...",
                        "%.2f Mbps".format(report.transferRateBit.toDouble() / 1_000_000),
                        percent.toString()
                    )
                }
            })

            for (url in testServerUrls) {
                try {
                    speedTestSocket.startDownload(url)
                    break
                } catch (e: Exception) {
                    Log.e(TAG, "Error downloading from $url", e)
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            tvStatus.text = values[0]
            tvSpeed.text = values[1]

            // Update the ProgressBar
            val progress = (values[2]?.toFloat() ?: 0f) * 100
            progressBar.progress = progress.toInt()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            btnStart.isEnabled = true
        }
    }
}
