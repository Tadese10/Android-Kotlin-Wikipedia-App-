package com.example.mywikiapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.mywikiapp.R
import com.example.mywikiapp.WikiApplication
import com.example.mywikiapp.managers.WikiManager
import com.example.mywikiapp.models.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_details.*
import org.jetbrains.anko.toast
import java.lang.Exception

class ArticleDetails2Activity : AppCompatActivity() {

    private var wikiManager : WikiManager? = null
    private var currentPage : WikiPage? = null
    private var articleWebView : WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_details_layout)
        wikiManager = (applicationContext as WikiApplication).wikiManager
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        try {
            //get page from the extras
            articleWebView = findViewById<WebView>(R.id.articleWebView)
            val wikipageJson = intent.getStringExtra("page")
            currentPage  = Gson().fromJson<WikiPage>(wikipageJson, WikiPage::class.java)
            articleWebView!!.settings.setJavaScriptEnabled(true)
            articleWebView!!.webViewClient = object : WebViewClient(){

            }
            articleWebView!!.loadUrl(currentPage!!.fullurl)
            wikiManager!!.addHistory(currentPage!!)
            supportActionBar!!.title = currentPage!!.title
        }
        catch (ex: Exception){
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId == R.id.action_favorite){
            try {
                if(wikiManager!!.getIsFavorite(currentPage!!.pageid!!)){
                    wikiManager!!.removeFavorite(currentPage!!.pageid!!)
                    toast("Article removed from the favorites")
                }else{
                    wikiManager!!.addFavorite(currentPage!!)
                    toast("Article added to the favorites")
                }
            }catch (ex:Exception){
                toast("Unable to process this article.")
            }

        }
        return true
    }

}
