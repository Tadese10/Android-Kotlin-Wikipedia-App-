package com.example.mywikiapp.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mywikiapp.R
import com.example.mywikiapp.models.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_details.*
import java.lang.Exception

class ArticleDetailsActivity : AppCompatActivity(){

    private var currentPage : WikiPage? = null
    private var articleWebView : WebView? = null
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.new_article_details_page)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        try {
            //get page from the extras
            articleWebView = findViewById<WebView>(R.id.details_web_view)
            val wikipageJson = intent.getStringExtra("page")
            currentPage  = Gson().fromJson<WikiPage>(wikipageJson, WikiPage::class.java)
            details_web_view?.webViewClient = object : WebViewClient(){

                override fun shouldOverrideUrlLoading( view: WebView?,   request: WebResourceRequest? ): Boolean {
                    return true
                }
            }
            details_web_view?.loadUrl(currentPage!!.fullurl)
        }
        catch (ex:Exception){
            Toast.makeText(this, ex.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPostResume() {
        super.onPostResume()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return true
    }
}