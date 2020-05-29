package com.example.mywikiapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywikiapp.R
import com.example.mywikiapp.models.WikiPage
import com.example.mywikiapp.ui.holders.CardHolder
import com.example.mywikiapp.ui.holders.ListItemHolder

class ArticleListItemRecyclerAdapter : RecyclerView.Adapter<ListItemHolder>() {

    val currentResults : ArrayList<WikiPage> = ArrayList<WikiPage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        return ListItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_list_item,  parent, false))
    }

    override fun getItemCount(): Int {
       return  currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        var page = currentResults[position]
        holder.updateWithPage(page)
    }
}