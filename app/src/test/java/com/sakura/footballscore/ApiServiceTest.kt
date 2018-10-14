package com.sakura.footballscore

import com.sakura.footballscore.service.ApiRepository
import com.sakura.footballscore.service.ApiService
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiServiceTest {

    @Test
    fun testDoRequest() {
        val apiService = mock(ApiService::class.java)
        val url = "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php?id=4328"
        apiService.doRequest(url)
        verify(apiService).doRequest(url)
    }
}