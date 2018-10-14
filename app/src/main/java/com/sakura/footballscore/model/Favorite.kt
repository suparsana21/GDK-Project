package com.sakura.footballscore.model

data class Favorite(
    val id: Long?,
    val matchId : String?,
    val strDate : String?,
    val strHomeTeam : String?,
    val strAwayTeam : String?,
    val strHomeScore : String?,
    val strAwayScore : String?
) {
    companion object {
        const val TABLE_NAME: String = "TABLE_FAVORITE"
        const val ID : String = "ID_"
        const val MATCH_ID : String = "MATCH_ID"
        const val STR_DATE : String = "STR_DATE"
        const val STR_HOME_TEAM : String = "STR_HOME_TEAM"
        const val STR_AWAY_TEAM : String = "STR_AWAY_TEAM"
        const val STR_HOME_SCORE : String = "STR_HOME_SCORE"
        const val STR_AWAY_SCORE : String = "STR_AWAY_SCORE"
    }
}