package com.example.mywikiapp.repositories

import com.example.mywikiapp.models.WikiPage
import com.example.mywikiapp.models.WikiThumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class FavoritesRepository(val databaseOpenHelper: ArticleDatabaseOpenHelper) {
    private val TABLE_NAME: String = "Favorites"

    fun addToFavorite(page: WikiPage){
        databaseOpenHelper.use {
            insert(TABLE_NAME, "id" to page.pageid,
                "title" to page.title,
            "url" to page.fullurl,
            "thumbnailJson" to Gson().toJson(page.thumbnail)
            )
        }

    }

    fun removeFavoriteById(pageId: Int){
        databaseOpenHelper.use {
            delete(TABLE_NAME, "id = {pageId}", "pageId" to pageId)
        }
    }

    fun isArticleFavorite(pageId: Int): Boolean{
        //get favorites and filter
        var pages = getAllFavorites()
        return pages.any{
            page ->
            page.pageid == pageId
        }
    }

    fun getAllFavorites():ArrayList<WikiPage>{
        var pages = ArrayList<WikiPage>()

        val articleRowParser = rowParser{id: Int, title: String, url : String, thumbnailJson : String ->
            val page = WikiPage()
            page.pageid = id
            page.title = title
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }

        databaseOpenHelper.use {
            select(TABLE_NAME).parseList(articleRowParser)
        }

        return pages
    }

}