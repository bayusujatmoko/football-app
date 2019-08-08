package match.football.submission2.submission2.app.match

import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.Match
import match.football.submission2.submission2.app.model.MatchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {
    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson)
    }

    @Test
    fun getLastMatchDataTest() {
        val teams: MutableList<Match> = mutableListOf()
        val response = MatchResponse(teams)
        val league = "4328"

        doAsync {
            `when`(gson.fromJson(apiRepository
                .doRequest(ApiInterface.getLastMatch(league)),
                MatchResponse::class.java
            )).thenReturn(response)

            uiThread {
                presenter.getLastMatchData(league)
                Mockito.verify(view).showLoading()
                Mockito.verify(view).showMatchList(teams)
                Mockito.verify(view).hideLoading()
            }
        }
    }
}