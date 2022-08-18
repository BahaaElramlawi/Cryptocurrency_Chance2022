package com.bahaa.cryptocurrency_chance2022.ui.Fragment


import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bahaa.cryptocurrency_chance2022.databinding.CoinDetailsFragmentBinding
import com.bahaa.cryptocurrency_chance2022.model.CoinDetail
import com.bahaa.cryptocurrency_chance2022.network.Client
import com.bahaa.cryptocurrency_chance2022.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinDetailsFragment(private val id: String) :
    BaseFragment<CoinDetailsFragmentBinding>(CoinDetailsFragmentBinding::inflate) {
    override fun addCallBacks() {
        getCoinDetails()
    }


    private fun getCoinDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            Client.getCoinDetails(id = id).collect {
                when (it) {
                    is State.Success -> {
                        withContext(Dispatchers.Main) {
                            onSuccess(it.data)
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

    private fun onSuccess(coin: CoinDetail?) {
        binding.apply {
            detailProgressBar.visibility = View.GONE
        }
        setupCoinDetail(coin)
    }

    private fun onLoading() {
        binding.apply {
            detailProgressBar.visibility = View.VISIBLE
        }
    }

    private fun onError() {
        binding.apply {
            detailProgressBar.visibility = View.GONE
        }
    }


    private fun setupCoinDetail(coinDetail: CoinDetail?) {
        binding.apply {
            coinItemNameTextView.text = coinDetail?.name
            coinItemSymbolTextView.text = coinDetail?.symbol
            coinsItemStatusTextView.text = coinDetail?.isActive.toString()
            coinItemDescTextView.text = coinDetail?.description
        }
    }

    override val LOG_TAG: String
        get() = "CoinDetailsFragment"


}