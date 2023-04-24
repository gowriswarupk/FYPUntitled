package com.technologkal.spyderApp.ui.fragment.onBoarding

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.technologkal.spyderApp.model.SlideContent
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.R

class OnBoardingViewModel(application: Application) : AndroidViewModel(application) {
    private val list = listOf(
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.spyder_logo)!!,
            "Spyder - Raspberry Pi",
            "Make sure to install the Spyder application on your Raspberry Pi!" +
                    "\n check out the GitHub Repo: github.com/gowriswarupk/spyder-rpi"
        ),
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.ic_github)!!,
            "Check out our GitHub Repo!",
            "To fully utilize the application capabilities, check out the GitHub Repo: github.com/gowriswarupk/spyderApp"
        ),
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.ic_firebase)!!,
            "Connect to Firebase",
            "To use the cloud features, follow the steps on the github page to connect to Firebase"
        ),
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.ic_securelock)!!,
            "Secure your Network",
            "Keep your private network safe and your devices accounted for!"
        ),
        SlideContent(
            ContextCompat.getDrawable(application.applicationContext, R.drawable.ic_complete)!!,
            "Ready? Let's begin!",
            " "
        )
    )

    private val _dataSet = MutableLiveData<List<SlideContent>>().apply { value = list }
    val dataSet: LiveData<List<SlideContent>>
        get() = _dataSet

    private val _buttonVisibility = MutableLiveData<Boolean>().apply { value = false }
    val buttonVisibility: LiveData<Boolean>
        get() = _buttonVisibility

    val pagerCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            _buttonVisibility.value = position == list.size - 1
            super.onPageSelected(position)
        }
    }
    private val _startNavigation = MutableLiveData<Boolean>().apply { value = false }
    val startNavigation: LiveData<Boolean>
        get() = _startNavigation

    fun navigateToAuth() {
        _startNavigation.value = true
    }

    fun doneNavigation() {
        _startNavigation.value = false
    }
}