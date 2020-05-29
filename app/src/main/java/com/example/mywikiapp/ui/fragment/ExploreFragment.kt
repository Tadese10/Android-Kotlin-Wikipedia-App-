package com.example.mywikiapp.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.mywikiapp.R
import com.example.mywikiapp.WikiApplication
import com.example.mywikiapp.managers.WikiManager
import com.example.mywikiapp.providers.ArticleDataProvider
import com.example.mywikiapp.ui.activities.SearchActivity
import com.example.mywikiapp.ui.adapters.ArticleCardRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_explore.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {
    //private val articleDataProvider : ArticleDataProvider = ArticleDataProvider()
    private var wikiManager: WikiManager? = null
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        search_card_view.setOnClickListener { _ ->
            var searchIntent = Intent(context, SearchActivity::class.java)
            context?.startActivity(searchIntent)
        }
        explore_article_recycler.layoutManager  = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        explore_article_recycler.adapter = adapter

        refresher.setOnRefreshListener {

            getRandomArticles()
        }

        getRandomArticles()
    }

    private fun getRandomArticles(){
        try {

            refresher.isRefreshing = true
            wikiManager!!.getRandom(15) { wikiResult ->
                adapter.currentResult.clear()
                adapter.currentResult.addAll(wikiResult.query!!.pages)
                activity!!.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher.isRefreshing = false
                }
            }
        }catch (ex: Exception){
            var builder = AlertDialog.Builder(activity)
            builder.setMessage(ex.message)
            builder.setTitle("Error")
            var dialog = builder.create()
            dialog.show()
        }
    }

}
