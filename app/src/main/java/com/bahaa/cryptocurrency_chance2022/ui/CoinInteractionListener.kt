package com.bahaa.cryptocurrency_chance2022.ui

import com.bahaa.cryptocurrency_chance2022.model.Coin

interface CoinInteractionListener {
    fun onClickItem(coin: Coin)
}