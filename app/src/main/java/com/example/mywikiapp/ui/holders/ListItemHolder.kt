package com.example.mywikiapp.ui.holders

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mywikiapp.R
import com.example.mywikiapp.models.WikiPage
import com.example.mywikiapp.ui.activities.ArticleDetails2Activity
import com.example.mywikiapp.ui.activities.ArticleDetailsActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_card_item.view.*

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView : ImageView = itemView.findViewById(R.id.result_icon)
    private val articleTextView : TextView  = itemView.findViewById(R.id.result_title)
    private var currentPage: WikiPage? = null

    init {
        itemView.setOnClickListener{view:View? ->
            var pageJson = Gson().toJson(currentPage)
            var detailsPageIntent = Intent(itemView.context, ArticleDetails2Activity::class.java)
            detailsPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(detailsPageIntent)
        }
    }

    fun updateWithPage(page: WikiPage){
        currentPage = page
        articleTextView.text = page.title
        if(page.thumbnail != null)
            Picasso.with(itemView.context).load(page.thumbnail!!.source).into(articleImageView)

    }
}