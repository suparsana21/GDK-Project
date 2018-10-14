package com.sakura.footballscore

import android.content.Context
import com.google.gson.Gson
import com.sakura.footballscore.model.EventResponse
import com.sakura.footballscore.model.EventsItem
import com.sakura.footballscore.presenter.HomePresenter
import com.sakura.footballscore.presenter.HomeView
import com.sakura.footballscore.presenter.test.TestContextProvider
import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*


class HomePresenterTest {
    @Mock
    private
    lateinit var view: HomeView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var apiService : ApiService

    @Mock
    private
    lateinit var ctx : Context


    private lateinit var presenter: HomePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = HomePresenter(view,apiRepository,apiService,gson,ctx, TestContextProvider())


    }

    @Test
    fun testGetLastMatch() {
        val items: MutableList<EventsItem> = mutableListOf()
        val response = EventResponse(items)

        `when`(gson.fromJson(apiService
                .doRequest(apiRepository.getLastMatch()),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getLastMatch()

        verify(view).showLoading()
        verify(view).hideLoading()

    }

    @Test
    fun testGetNextMatch() {
        val items: MutableList<EventsItem> = mutableListOf()
        val response = EventResponse(items)

        `when`(gson.fromJson(apiService
                .doRequest(apiRepository.getNextMatch()),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getNextMatch()

        verify(view).showLoading()
        verify(view).hideLoading()

    }


}