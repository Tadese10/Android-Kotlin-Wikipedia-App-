package com.example.mywikiapp.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import org.w3c.dom.Text

class ArticleDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "ArticlesDatabase.db") {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(tableName = "Favorites", ifNotExists = true,
            columns = *arrayOf("id" to INTEGER + PRIMARY_KEY,
            "title" to TEXT,
            "url" to TEXT,
            "thumbnailJson" to TEXT
        )
        )

        db?.createTable(tableName = "History", ifNotExists = true,
            columns = *arrayOf("id" to INTEGER + PRIMARY_KEY,
                "title" to TEXT,
                "url" to TEXT,
                "thumbnailJson" to TEXT
            )
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}