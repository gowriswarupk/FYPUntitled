package com.technologkal.spyderApp.utils

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.technologkal.spyderApp.model.UserModel


class MyApplication : MultiDexApplication(), DefaultLifecycleObserver {

    companion object {
        var currentUser: UserModel? = null
    }

    override fun onCreate() {
        super<MultiDexApplication>.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        MultiDex.install(this)
    }

    override fun onStop(owner: LifecycleOwner) {
        val mAuth: FirebaseAuth? = FirebaseAuth.getInstance()

        if (mAuth?.currentUser != null && currentUser != null) {
            currentUser!!.active = false
            FirestoreUtil.updateUser(currentUser!!) {}
        }
        Log.d("Awww", "App in background")
    }

    override fun onStart(owner: LifecycleOwner) {
        val mAuth: FirebaseAuth? = FirebaseAuth.getInstance()

        if (mAuth?.currentUser != null && currentUser != null) {
            currentUser!!.active = true
            FirestoreUtil.updateUser(currentUser!!) {}
        }

        Log.d("Yeeey", "App in foreground")
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}

/**
 * If you are using Android Architecture Components you can use the ProcessLifecycleOwner
 * and LifecycleObserver like so (set this class to the app name in the manifest)
 * // <application
//   android:name=".ArchLifecycleApp"
//    ....
 */
class ArchLifecycleApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Log.d("Awww", "App in background")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Log.d("Yeeey", "App in foreground")
    }

}