package com.bahaa.cryptocurrency_chance2022.ui.Fragment


import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahaa.cryptocurrency_chance2022.databinding.CoinListFragmentBinding
import com.bahaa.cryptocurrency_chance2022.model.Coin
import com.bahaa.cryptocurrency_chance2022.network.Client
import com.bahaa.cryptocurrency_chance2022.ui.CoinInteractionListener
import com.bahaa.cryptocurrency_chance2022.ui.CoinsListAdapter
import com.bahaa.cryptocurrency_chance2022.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinListFragment(private val onItemClick: (Coin) -> Unit) :
    BaseFragment<CoinListFragmentBinding>(CoinListFragmentBinding::inflate),
    CoinInteractionListener {


    override fun addCallBacks() {
        getCoins()
    }

    private fun getCoins() {
        lifecycleScope.launch(Dispatchers.IO) {
            Client.getCoinList().collect {
                when (it) {
                    is State.Success -> {
                        withContext(Dispatchers.Main) {
                            onSuccess(it.data.orEmpty())
                        }
                    }
                    is State.Error -> {
                        withContext(Dispatchers.Main) {
                            onError()
                        }
                    }
                    State.Loading -> {
                        withContext(Dispatchers.Main) {
                            onLoading()
                        }
                    }
                }
            }
        }
    }

    private fun onSuccess(list: List<Coin>) {
        binding.apply {
            listProgressBar.visibility = View.GONE
        }
        setupDailyRecycler(list)
    }

    private fun onLoading() {
        binding.apply {
            listProgressBar.visibility = View.VISIBLE
        }
    }

    private fun onError() {
        binding.apply {
            listProgressBar.visibility = View.GONE
        }
    }

    private fun setupDailyRecycler(coinList: List<Coin>) {
        binding.coinsListRecyclerView.apply {
            adapter = CoinsListAdapter(coinList, this@CoinListFragment)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override val LOG_TAG: String
        get() = "CoinListFragment"

    override fun onClickItem(coin: Coin) {
        onItemClick(coin)
    }

}





