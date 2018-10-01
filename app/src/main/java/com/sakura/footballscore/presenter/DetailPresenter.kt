package com.sakura.footballscore.presenter

import com.google.gson.Gson
import com.sakura.footballscore.model.DetailEventResponse
import com.sakura.footballscore.model.EventResponse
import com.sakura.footballscore.model.TeamResponse
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class DetailPresenter (
        private val view : DetailView,
        private val apiRepository : ApiRepository,
        private val apiService: ApiService,
        private val gson: Gson
) {
    fun getLookupEvent(id : String?) {

        doAsync {
            val data = gson.fromJson(apiService
                    .doRequest(apiRepository.getLookupEvent(id)),
                    DetailEventResponse::class.java
            )

            uiThread {
                view.showDetailEvent(data.events)
            }
        }

    }

    fun getLookupTeam(id : String?, type : String?) {

        doAsync {
            val data = gson.fromJson(apiService
                    .doRequest(apiRepository.getLookupTeam(id)),
                    TeamResponse::class.java
            )

            uiThread {
                if(type == "HOME") {
                    view.showDetailHomeTeam(data.teams?.get(0))
                } else {
                    view.showDetailAwayTeam(data.teams?.get(0))
                }
            }
        }

    }

}
