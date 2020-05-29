package com.example.mywikiapp

import android.app.Application
import com.example.mywikiapp.managers.WikiManager
import com.example.mywikiapp.providers.ArticleDataProvider
import com.example.mywikiapp.repositories.ArticleDatabaseOpenHelper
import com.example.mywikiapp.repositories.FavoritesRepository
import com.example.mywikiapp.repositories.HistoryRepository

class WikiApplication : Application() {

   private var dbHelper :ArticleDatabaseOpenHelper? = null
   private var favoritesRepository: FavoritesRepository? = null
   private var historyRepository: HistoryRepository? =null
   private var wikiProvider : ArticleDataProvider? = null
    var wikiManager: WikiManager? = null


    override fun onCreate() {
        super.onCreate()
        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!,favoritesRepository!!,historyRepository!!)
    }

}