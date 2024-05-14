package com.example.myapplication4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CardAdapter(
    private val cardList: List<Card>,
    private val clickListener: (Card) -> Unit,
    private val deleteListener: (Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val answerTextView: TextView = itemView.findViewById(R.id.answerTextView)
        val translateTextView: TextView = itemView.findViewById(R.id.translateTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val deleteImageView: ImageView = itemView.findViewById(R.id.deleteImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentCard = cardList[position]
        holder.answerTextView.text = currentCard.answer
        holder.translateTextView.text = currentCard.translate
        Glide.with(holder.itemView.context).load(currentCard.imageUri).into(holder.imageView)

        holder.itemView.setOnClickListener { clickListener(currentCard) }
        holder.deleteImageView.setOnClickListener { deleteListener(currentCard) }
    }

    override fun getItemCount() = cardList.size
}
