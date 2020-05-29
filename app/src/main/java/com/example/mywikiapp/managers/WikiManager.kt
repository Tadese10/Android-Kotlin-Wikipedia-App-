package com.example.mywikiapp.managers

import com.example.mywikiapp.models.WikiPage
import com.example.mywikiapp.models.WikiResult
import com.example.mywikiapp.providers.ArticleDataProvider
import com.example.mywikiapp.repositories.FavoritesRepository
import com.example.mywikiapp.repositories.HistoryRepository

class WikiManager(private  var provider: ArticleDataProvider,
                  private var favoritesRepository: FavoritesRepository,
                  private  var historyRepository: HistoryRepository) {

    private var favoritesCache : ArrayList<WikiPage>? = null
    private var historyCache : ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int,take:Int, handler: (result: WikiResult) -> Unit?){
        return provider.search(term,skip,take, handler)
    }

    fun getRandom(take:Int, handler: (result:WikiResult) -> Unit?){
        provider.getRandom(take, handler)
    }

    fun getHistory(): ArrayList<WikiPage>{
        if(historyCache == null)
            historyCache = historyRepository.getAllHistories()
        return historyCache as ArrayList<WikiPage>
    }

    fun getFavorites(): ArrayList<WikiPage>{
        if(favoritesCache == null)
            favoritesCache = favoritesRepository.getAllFavorites()
        return favoritesCache as ArrayList<WikiPage>
    }
    fun addFavorite(page: WikiPage){
        favoritesCache?.add(page)
        favoritesRepository.addToFavorite(page)
    }

    fun removeFavorite(pageId: Int){
        favoritesRepository?.removeFavoriteById(pageId)
        favoritesCache = favoritesCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId:Int): Boolean{
        return favoritesRepository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage){
        historyCache?.add(page)
        historyRepository?.addToHistory(page)
    }

    fun clearHistory(){
        historyCache?.clear()
        val histories = historyRepository?.getAllHistories()
        histories?.forEach{ historyRepository.removeHistoryById(it.pageid!!)}
    }

}