package com.example.mywikiapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mywikiapp.R
import com.example.mywikiapp.WikiApplication
import com.example.mywikiapp.managers.WikiManager
import com.example.mywikiapp.models.WikiPage
import com.example.mywikiapp.ui.adapters.ArticleCardRecyclerAdapter
import com.example.mywikiapp.ui.adapters.ArticleListItemRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {
    private val adapter: ArticleListItemRecyclerAdapter =  ArticleListItemRecyclerAdapter()
    private var wikiManager : WikiManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager

    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val favoriteArticles = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favoriteArticles as ArrayList<WikiPage>)
            activity!!.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.history_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // return super.onOptionsItemSelected(item)
        if(item!!.itemId == R.id.action_clear_history){
            activity!!.alert("Are you sure you want to clear your history", "Confirm"){
                yesButton {
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager!!.clearHistory()
                    }
                    activity!!.runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }

                noButton {

                }
            }.show()

        }

        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        history_article_recycler.layoutManager = LinearLayoutManager(context)
        history_article_recycler.adapter = adapter
    }

}
