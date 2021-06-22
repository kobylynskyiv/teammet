package com.example.teammate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.teammate.databinding.ActivityMainBinding
import com.example.teammate.utils.CreateUserName
import com.example.teammate.utils.User
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uuid = CreateUserName().loadUUID(baseContext)
        val db = FirebaseFirestore.getInstance()
        var findUser = false

        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if(document.data["userId"] == uuid){
                            findUser = true
                            binding.toolbarTitle.text = document.data["nickname"] as String
                        }
                    }

                    if(!findUser){
                        val randomNickName: String = CreateUserName().generateUser()
                        binding.toolbarTitle.text = randomNickName
                        val user = User("", randomNickName, uuid)
                        saveUser(user)
                    }

                } else {
                    Log.w("CreateUserName", "Error getting documents.", task.exception)
                }
            }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun saveUser(user: User) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(user.userId).set(user)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}