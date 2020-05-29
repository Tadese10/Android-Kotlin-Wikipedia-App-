package com.example.mywikiapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywikiapp.R
import com.example.mywikiapp.models.WikiPage
import com.example.mywikiapp.ui.holders.CardHolder
import com.example.mywikiapp.ui.holders.ListItemHolder

class ArticleCardRecyclerAdapter : RecyclerView.Adapter<CardHolder>() {

    val currentResult: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_card_item,  parent, false))
    }

    override fun getItemCount(): Int {
       return  currentResult.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val page = currentResult[position]
        //update viewholder
        holder.updateWithPage(page)
    }
}