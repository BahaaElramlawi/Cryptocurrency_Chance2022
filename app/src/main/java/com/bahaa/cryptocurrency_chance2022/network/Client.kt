package com.bahaa.cryptocurrency_chance2022.network


import com.bahaa.cryptocurrency_chance2022.model.Coin
import com.bahaa.cryptocurrency_chance2022.model.CoinDetail
import com.bahaa.cryptocurrency_chance2022.util.Constants
import com.bahaa.cryptocurrency_chance2022.util.State
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.*

object Client {
    private val client = OkHttpClient()

    suspend fun getCoinList(): Flow<State<List<Coin>>> {
        return flow {
            val request = Request.Builder().url(Constants.BASE_URL + "/v1/coins").build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val result =
                    Gson().fromJson(response.body?.string(), Array<Coin>::class.java).asList()
                        .subList(0, 10)
                emit(State.Success(result))
            } else {
                emit(State.Error(response.message))

            }
        }
    }

    fun getCoinDetails(id: String): Flow<State<CoinDetail>> {
        return flow {
            val request = Request.Builder().url(Constants.BASE_URL + "/v1/coins/$id").build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val result = Gson().fromJson(response.body?.string(), CoinDetail::class.java)
                emit(State.Success(result))
            } else {
                emit(State.Error(response.message))

            }
        }
    }


}