package com.bahaa.cryptocurrency_chance2022.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahaa.cryptocurrency_chance2022.model.Coin
import com.bahaa.cryptocurrency_chance2022.R
import com.bahaa.cryptocurrency_chance2022.databinding.ItemCoinsListBinding

class CoinsListAdapter(
    private val list: List<Coin>,
    private val listener: CoinInteractionListener,
) :
    RecyclerView.Adapter<CoinsListAdapter.CoinsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coins_list, parent, false)
        return CoinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            coinsItemNameTextView.text = currentItem.name
            coinsItemSymbolTextView.text = currentItem.symbol
            coinsItemRankTextView.text = currentItem.rank.toString()
            coinsItemStatusTextView.text = currentItem.isActive.toString()
            root.setOnClickListener {
                listener.onClickItem(currentItem)
            }
        }

    }

    override fun getItemCount() = list.size

    class CoinsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCoinsListBinding.bind(itemView)
    }
}