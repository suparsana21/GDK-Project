package com.sakura.footballscore.service

import java.net.URL

class ApiService {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}