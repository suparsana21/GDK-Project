package com.sakura.footballscore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.sakura.footballscore.model.DetailEventItem
import com.sakura.footballscore.model.TeamsItem
import com.sakura.footballscore.presenter.DetailPresenter
import com.sakura.footballscore.presenter.DetailView
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity(),DetailView {

    private lateinit var presenter: DetailPresenter

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

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun showDetailEvent(data: List<DetailEventItem>?) {
        val item = data?.get(0)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date = dateFormat.parse(item?.dateEvent)
        dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy")

        tvDate.text = dateFormat.format(date).toString()

        tvHomeTeam.text = item?.strHomeTeam
        tvAwayTeam.text = item?.strAwayTeam

        homeShots.text = item?.intHomeShots
        awayShots.text = item?.intAwayShots

        homeGoalDetail.text = item?.strHomeGoalDetails?.replace(";","\n")
        awayGoalDetail.text = item?.strAwayGoalDetails?.replace(";","\n")

        homeGoalKeeper.text = item?.strHomeLineupGoalkeeper?.replace(";","\n")
        awayGoalKeeper.text = item?.strAwayLineupGoalkeeper?.replace(";","\n")


        homeDefense.text = item?.strHomeLineupDefense?.replace(";","\n")
        awayDefense.text = item?.strAwayLineupDefense?.replace(";","\n")


        homeMidfield.text = item?.strHomeLineupMidfield?.replace(";","\n")
        awayMidfield.text = item?.strAwayLineupMidfield?.replace(";","\n")


        homeForward.text = item?.strHomeLineupForward?.replace(";","\n")
        awayForward.text = item?.strAwayLineupForward?.replace(";","\n")


        homeSubstitution.text = item?.strHomeLineupSubstitutes?.replace(";","\n")
        awaySubstitution.text = item?.strAwayLineupSubstitutes?.replace(";","\n")

        presenter.getLookupTeam(item?.idAwayTeam,"AWAY")
        presenter.getLookupTeam(item?.idHomeTeam,"HOME")
    }

    override fun showDetailHomeTeam(data: TeamsItem?) {
        Picasso.get().load(data?.strTeamBadge).into(imgHomeLogo)
    }

    override fun showDetailAwayTeam(data: TeamsItem?) {
        Picasso.get().load(data?.strTeamBadge).into(imgAwayLogo)
    }

}
