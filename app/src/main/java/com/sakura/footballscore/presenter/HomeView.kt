package com.sakura.footballscore.presenter

import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.model.Favorite

interface HomeView {
    fun showLoading()
    fun hideLoading()
    fun showPastEvent(data : List<EventsItem>)
    fun showNextEvent(data : List<EventsItem>)
    fun showFavorite(data : List<Favorite>)
}