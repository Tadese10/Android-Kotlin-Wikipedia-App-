package com.example.mywikiapp.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.mywikiapp.R
import com.example.mywikiapp.WikiApplication
import com.example.mywikiapp.managers.WikiManager
import com.example.mywikiapp.models.WikiPage
import com.example.mywikiapp.ui.adapters.ArticleCardRecyclerAdapter
import com.example.mywikiapp.ui.adapters.ArticleListItemRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.doAsync

/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment : Fragment() {
    private var wikiManager : WikiManager? = null
    private val adapter: ArticleCardRecyclerAdapter =  ArticleCardRecyclerAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favorites_article_recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favorites_article_recycler.adapter  = adapter
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val favoriteArticles = wikiManager!!.getFavorites()
            adapter.currentResult.clear()
            adapter.currentResult.addAll(favoriteArticles as ArrayList<WikiPage>)
            activity!!.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

}
