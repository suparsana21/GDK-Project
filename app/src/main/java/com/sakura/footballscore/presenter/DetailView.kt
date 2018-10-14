package com.sakura.footballscore.presenter

import com.sakura.footballscore.model.DetailEventItem
import com.sakura.footballscore.model.TeamsItem

interface DetailView {
    fun showDetailEvent(data : List<DetailEventItem>)
    fun showDetailHomeTeam(data : TeamsItem?)
    fun showDetailAwayTeam(data : TeamsItem?)
}