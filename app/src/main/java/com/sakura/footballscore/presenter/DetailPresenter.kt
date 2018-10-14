package com.sakura.footballscore.presenter

import com.google.gson.Gson
import com.sakura.footballscore.model.DetailEventResponse
import com.sakura.footballscore.model.EventResponse
import com.sakura.footballscore.model.TeamResponse
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import com.sakura.footballscore.service.CoroutineContextProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class DetailPresenter (
        private val view : DetailView,
        private val apiRepository : ApiRepository,
        private val apiService: ApiService,
        private val gson: Gson,
        private val context: CoroutineContextProvider = CoroutineContextProvider()

) {
    fun getLookupEvent(id : String) {


        async(context.main) {
            val data = bg{
                gson.fromJson(apiService
                        .doRequest(apiRepository.getLookupEvent(id)),
                        DetailEventResponse::class.java
                )
            }
            view.showDetailEvent(data.await().events)

        }


    }

    fun getLookupTeam(id : String?, type : String?) {


        async(context.main) {
            val data = bg {
                gson.fromJson(apiService
                        .doRequest(apiRepository.getLookupTeam(id)),
                        TeamResponse::class.java
                )
            }

            if(type == "HOME") {
                view.showDetailHomeTeam(data.await().teams?.get(0))
            } else {
                view.showDetailAwayTeam(data.await().teams?.get(0))
            }
        }


    }

}
