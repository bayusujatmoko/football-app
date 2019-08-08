package match.football.submission2.submission2.app.player

import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.PlayerDetail
import match.football.submission2.submission2.app.model.PlayerResponseDetail
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterDetailTest {
    @Mock
    private
    lateinit var view: PlayerViewDetail

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenterDetail

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenterDetail(view, apiRepository, gson)
    }

    @Test
    fun getPlayerDetailTest() {
        val teams: MutableList<PlayerDetail> = mutableListOf()
        val response = PlayerResponseDetail(teams)
        val league = "34145937"

        doAsync {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(ApiInterface.getPlayer(league)),
                    PlayerResponseDetail::class.java
                )
            ).thenReturn(response)

            uiThread {
                presenter.getPlayerDetail(league)
                Mockito.verify(view).showLoading()
                Mockito.verify(view).showPlayerDetail(teams)
                Mockito.verify(view).hideLoading()
            }
        }
    }
}