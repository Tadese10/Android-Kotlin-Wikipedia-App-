package com.example.mywikiapp.providers

import com.example.mywikiapp.models.Urls
import com.example.mywikiapp.models.WikiResult
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import java.io.Reader
import java.lang.Exception

class ArticleDataProvider {

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Pluralsight Wikipedia")
    }

    fun search(term: String, skin: Int, take: Int, responseHandler: (result : WikiResult) -> Unit?){

        Urls.getSearchUrl(term, skin, take).httpGet().responseObject(WikipediaDataDeserializer()){_,_,result ->
            val(data, _) = result
            responseHandler.invoke(data as @ParameterName(name = "result") WikiResult)
        }
    }

    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?){
        Urls.getRandomUrl(take).httpGet().responseObject(WikipediaDataDeserializer()){request,response, result ->
            if(response.statusCode != 200){
                throw Exception("Unable to get articles")
            }
            val(data, _) = result
            responseHandler.invoke(data as @ParameterName(name = "result") WikiResult)
        }
    }

    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult> {

        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)
    }

}