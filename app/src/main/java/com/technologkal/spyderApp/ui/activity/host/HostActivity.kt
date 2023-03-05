package com.technologkal.spyderApp.ui.activity.host

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.technologkal.spyderApp.model.UserModel
import com.technologkal.spyderApp.utils.FirestoreUtil
import com.technologkal.spyderApp.utils.MyApplication
import com.technologkal.spyderApp.utils.Prefs
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.R
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.databinding.DrawerHeaderLayoutBinding


class HostActivity : AppCompatActivity() {
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: NavController
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var navViewBinding: DrawerHeaderLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        toolbar = findViewById(R.id.customToolbar)
        setSupportActionBar(toolbar)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        drawerLayout = findViewById(R.id.drawer_layout)
        navViewBinding = DrawerHeaderLayoutBinding.inflate(layoutInflater, findViewById(R.id.navView), true)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController

        val navInflater = navController.navInflater

        val graph = navInflater.inflate(R.navigation.main_graph)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get the FCM registration token.
            val token = task.result
            Log.d(TAG, "FCM registration token: $token")
            // Send the token to your server or use it to send push notifications.
        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.onBoarding ||
                destination.id == R.id.authFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.signUpFragment
            ) {
                toolbar.visibility = View.GONE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
        if (!Prefs.getInstance(this)!!.hasCompletedWalkthrough!!) {
            if (mAuth.currentUser == null) {
                graph.setStartDestination(R.id.authFragment)
            } else {
                getUserData()
                graph.setStartDestination(R.id.scriptsFragment)
            }
        } else {
            graph.setStartDestination(R.id.onBoarding)

        }
        navController.graph = graph

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        navView = findViewById(R.id.navView)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            it.isChecked
            drawerLayout.closeDrawers()
            when (it.itemId) {
                R.id.action_logout -> {
                    MyApplication.currentUser!!.active = false
                    FirestoreUtil.updateUser(MyApplication.currentUser!!) {
                        mAuth.signOut()
                    }
                    googleSignInClient.signOut()
                    MyApplication.currentUser = null
                    navController.navigate(R.id.action_logout)
                }
            }
            true
        }

    }

    private fun getUserData() {

        val ref = db.collection("users").document(mAuth.currentUser!!.uid)

        ref.get().addOnSuccessListener {
            val userInfo = it.toObject(UserModel::class.java)
            navViewBinding.user = userInfo
            MyApplication.currentUser = userInfo
            MyApplication.currentUser!!.active = true
            FirestoreUtil.updateUser(MyApplication.currentUser!!) {
            }
        }.addOnFailureListener {
            val intent = Intent(this, MyApplication::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

//    <!--- Toast Messages --!>

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onButton1Click(view: View?) {
        showToast("Running Device Scans...")
    }

    fun onButton2Click(view: View?) {
        showToast("Running Port Scans...")
    }

    fun onButton3Click(view: View?) {
        showToast("Running Security Analysis...")
    }

    fun onButton4Click(view: View?) {
        showToast("Honeypot Status Changed!")
    }

//    fun onButton5Click(view: View?) {
//        showToast("Button 5 clicked")
//    }

    fun onButton6Click(view: View?) {
        showToast("Opening Google Cloud Console...")
    }


}