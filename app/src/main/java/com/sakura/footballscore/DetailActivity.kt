package com.sakura.footballscore

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.sakura.footballscore.R.drawable.ic_add_to_favorites
import com.sakura.footballscore.R.drawable.ic_added_to_favorites
import com.sakura.footballscore.model.DetailEventItem
import com.sakura.footballscore.model.Favorite
import com.sakura.footballscore.model.TeamsItem
import com.sakura.footballscore.presenter.DetailPresenter
import com.sakura.footballscore.presenter.DetailView
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import com.sakura.footballscore.service.database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity(),DetailView {

    private lateinit var presenter: DetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var event : DetailEventItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("id")
        val request = ApiService()
        val gson = Gson()
        val repository = ApiRepository

        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = DetailPresenter(this, repository, request, gson)


        presenter.getLookupEvent(id)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        return true
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun showDetailEvent(data: List<DetailEventItem>) {
        val item = data.get(0)
            event = data.get(0)
        var dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date = dateFormat.parse(item?.dateEvent)
        dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy")

        tvDate.text = dateFormat.format(date).toString()

        tvHomeTeam.text = item.strHomeTeam
        tvAwayTeam.text = item.strAwayTeam

        homeShots.text = item.intHomeShots
        awayShots.text = item.intAwayShots

        homeGoalDetail.text = item.strHomeGoalDetails?.replace(";","\n")
        awayGoalDetail.text = item.strAwayGoalDetails?.replace(";","\n")

        homeGoalKeeper.text = item.strHomeLineupGoalkeeper?.replace(";","\n")
        awayGoalKeeper.text = item.strAwayLineupGoalkeeper?.replace(";","\n")


        homeDefense.text = item.strHomeLineupDefense?.replace(";","\n")
        awayDefense.text = item.strAwayLineupDefense?.replace(";","\n")


        homeMidfield.text = item.strHomeLineupMidfield?.replace(";","\n")
        awayMidfield.text = item.strAwayLineupMidfield?.replace(";","\n")


        homeForward.text = item.strHomeLineupForward?.replace(";","\n")
        awayForward.text = item.strAwayLineupForward?.replace(";","\n")


        homeSubstitution.text = item.strHomeLineupSubstitutes?.replace(";","\n")
        awaySubstitution.text = item.strAwayLineupSubstitutes?.replace(";","\n")

        presenter.getLookupTeam(item.idAwayTeam,"AWAY")
        presenter.getLookupTeam(item.idHomeTeam,"HOME")

        favoriteState()

    }

    override fun showDetailHomeTeam(data: TeamsItem?) {
        Picasso.get().load(data?.strTeamBadge).into(imgHomeLogo)
    }

    override fun showDetailAwayTeam(data: TeamsItem?) {
        Picasso.get().load(data?.strTeamBadge).into(imgAwayLogo)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_NAME,
                        Favorite.MATCH_ID to event.idEvent.toString(),
                        Favorite.STR_DATE to event.dateEvent.toString(),
                        Favorite.STR_HOME_TEAM to event.strHomeTeam.toString(),
                        Favorite.STR_AWAY_TEAM to event.strAwayTeam.toString(),
                        Favorite.STR_HOME_SCORE to event.intHomeScore?.toString(),
                        Favorite.STR_AWAY_SCORE to event.intAwayScore?.toString()
                )
            }
            snackbar( layoutDetail , "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(layoutDetail, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_NAME, "(MATCH_ID = {id})",
                        "id" to event.idEvent.toString())
            }
            snackbar(layoutDetail, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(layoutDetail, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_NAME)
                    .whereArgs("(MATCH_ID = {id})",
                            "id" to event.idEvent.toString())
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
            setFavorite()
        }
    }

}
