package com.sakura.footballscore.presenter

import com.google.gson.Gson
import com.sakura.footballscore.model.EventResponse
import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomePresenter (
        private val view : HomeView,
        private val apiRepository : ApiRepository,
        private val apiService: ApiService,
        private val gson: Gson
) {
    fun getLastMatch() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiService
                    .doRequest(ApiRepository.getLastMatch()),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showPastEvent(data.events)
            }
        }

    }

    fun getNextMatch() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiService
                    .doRequest(ApiRepository.getNextMatch()),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showPastEvent(data.events)
            }
        }

    }

}