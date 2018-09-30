package com.sakura.footballscore

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.sakura.footballscore.R.color.colorPrimary
import com.sakura.footballscore.adapter.HomeAdapter
import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.presenter.HomePresenter
import com.sakura.footballscore.presenter.HomeView
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MatchFragment : Fragment(), HomeView {
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var events: MutableList<EventsItem> = mutableListOf()
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: HomeAdapter

    companion object {
        fun newInstance() : MatchFragment {
            var lastMatchFragment = MatchFragment()
            var args = Bundle()
            lastMatchFragment.arguments = args
            return lastMatchFragment
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        val view =  UI {
            verticalLayout(){
                lparams(width = matchParent , height = wrapContent)
                padding = dip(16)
                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorPrimary,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout{
                        lparams (width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
                            lparams (width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar {
                        }.lparams{
                            centerHorizontally()
                        }
                    }
                }
            }

        }.view


        adapter = HomeAdapter(ctx,events)
        listTeam.adapter = adapter

        val request = ApiService()
        val gson = Gson()
        val repository = ApiRepository
        presenter = HomePresenter(this, repository, request, gson)

        firstLoad()
        refreshOnSwipe()



        return view

    }

    fun refreshOnSwipe() {
        if(tag == "nextEvent") {
            swipeRefresh.onRefresh { presenter.getNextMatch() }
        } else {
            swipeRefresh.onRefresh { presenter.getLastMatch() }
        }
    }

    fun firstLoad() {
        if(tag == "nextEvent") {
            presenter.getNextMatch()
        } else {
            presenter.getLastMatch()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNextEvent(data: List<EventsItem>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showPastEvent(data: List<EventsItem>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }


    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

}
