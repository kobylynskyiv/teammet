package com.kobylynskyiv.teammeet.presenter.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.databinding.ActivityMainBinding
import com.kobylynskyiv.teammeet.domain.viewModel.MainActivityViewModel


open class MainActivity : AppCompatActivity() {

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navGraph = (findNavController(R.id.nav_host_fragment_content_main).graph))
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainActivityModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        mainActivityModel.title.observe(this, {
            binding.toolbarTitle.text = it
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}