package com.technologkal.spyderApp.ui.activity.host

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.technologkal.spyderApp.model.UserModel
import com.technologkal.spyderApp.utils.FirestoreUtil
import com.technologkal.spyderApp.utils.MyApplication
import com.technologkal.spyderApp.utils.Prefs
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.R
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.databinding.DrawerHeaderLayoutBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.technologkal.ui.fragment.onBoarding.walkthroughactivity.databinding.FragmentHomeBinding


class HostActivity : AppCompatActivity() {
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: NavController
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var navViewBinding: DrawerHeaderLayoutBinding
    private lateinit var database: DatabaseReference
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        toolbar = findViewById(R.id.customToolbar)
        setSupportActionBar(toolbar)
        database = FirebaseDatabase.getInstance().reference

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
                graph.setStartDestination(R.id.homeFragment)
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
        toggleDatabaseValue("button1")
    }

    fun onButton2Click(view: View?) {
        showToast("Running Port Scans...")
        toggleDatabaseValue("button2")
    }

    fun onButton3Click(view: View?) {
        showToast("Running Security Analysis...")
        toggleDatabaseValue("button3")
    }

    fun onButton4Click(view: View?) {
        showToast("Honeypot Status Changed!")
        toggleDatabaseValue("button4")
    }

    fun onButton5Click(view: View?) {
        showToast("Opening Log History in console window!")
        val consoleLogsUrl = "https://console.cloud.google.com/logs/viewer"
        val logViewIntent = Intent(Intent.ACTION_VIEW, Uri.parse(consoleLogsUrl))
        startActivity(logViewIntent)
    }


    val firebaseConsoleUrl = "https://console.firebase.google.com/"

    fun onButton6Click(view: View?) {
        showToast("Opening Google Cloud Console...")
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(firebaseConsoleUrl))
        startActivity(browserIntent)    }

    private fun toggleDatabaseValue(buttonKey: String) {
        val buttonRef = database.child(buttonKey)
        buttonRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.getValue(Boolean::class.java) ?: false
                buttonRef.setValue(!currentValue)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "toggleDatabaseValue:onCancelled", databaseError.toException())
            }
        })
    }

    fun onButton7Click(view: View?) {
        showToast("Switching to Speed Test page!")
        navController.navigate(R.id.action_homeFragment_to_networkSpeedTestFragment)
    }

    fun onButton8Click(view: View?) {
        showToast("Switching to Network Monitoring page!")
        navController.navigate(R.id.action_homeFragment_to_networkMonitorConsoleFragment)
    }

}