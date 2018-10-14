package com.sakura.footballscore.presenter

import android.content.Context
import com.google.gson.Gson
import com.sakura.footballscore.model.EventResponse
import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.model.Favorite
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import com.sakura.footballscore.service.CoroutineContextProvider
import com.sakura.footballscore.service.database
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomePresenter (
        private val view : HomeView,
        private val apiRepository : ApiRepository,
        private val apiService: ApiService,
        private val gson: Gson,
        private val ctx : Context,
        private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLastMatch() {
        view.showLoading()

        async(context.main) {
            val data = bg{
                gson.fromJson(apiService
                        .doRequest(ApiRepository.getLastMatch()),
                        EventResponse::class.java
                )
            }

            view.hideLoading()
            view.showPastEvent(data.await().events as List<EventsItem>)
        }


    }

    fun getNextMatch() {
        view.showLoading()

        async(context.main) {
            val data = bg{
                gson.fromJson(apiService
                        .doRequest(ApiRepository.getNextMatch()),
                        EventResponse::class.java
                )
            }

            view.hideLoading()
            view.showPastEvent(data.await().events as List<EventsItem>)
        }


    }

    fun getFavMatch() {
        view.showLoading()


        ctx.database.use {
            val fav = select(Favorite.TABLE_NAME)
            val favoriteList = fav.parseList(classParser<Favorite>())

            view.hideLoading()
            view.showFavorite(favoriteList)
        }

    }

}