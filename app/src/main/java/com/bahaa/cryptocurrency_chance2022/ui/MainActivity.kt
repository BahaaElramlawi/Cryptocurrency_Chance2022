package com.bahaa.cryptocurrency_chance2022.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import com.bahaa.cryptocurrency_chance2022.R
import com.bahaa.cryptocurrency_chance2022.databinding.ActivityMainBinding
import com.bahaa.cryptocurrency_chance2022.ui.Fragment.CoinDetailsFragment
import com.bahaa.cryptocurrency_chance2022.ui.Fragment.CoinListFragment


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val myCoinListFragment = CoinListFragment {
        navigateCoinDetails(it.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initListCoinFragment()

    }

    private fun initListCoinFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainer, myCoinListFragment)
        transaction.commit()
    }

    private fun navigateCoinDetails(id: String) {
        val coinDetailsFragment = CoinDetailsFragment(id)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            binding?.let {
                replace(it.fragmentContainer.id, coinDetailsFragment)
                addToBackStack(coinDetailsFragment.toString())
            }
        }
    }
}