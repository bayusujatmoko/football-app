package match.football.submission2.submission2.app.player

import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.Player
import match.football.submission2.submission2.app.model.PlayerResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {
    @Mock
    private
    lateinit var view: PlayerView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, apiRepository, gson)
    }

    @Test
    fun getPlayerListTest() {
        val teams: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(teams)
        val league = "133604"

        doAsync {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(ApiInterface.getPlayerList(league)),
                    PlayerResponse::class.java
                )
            ).thenReturn(response)

            uiThread {
                presenter.getPlayerList(league)
                Mockito.verify(view).showLoading()
                Mockito.verify(view).showPlayerList(teams)
                Mockito.verify(view).hideLoading()
            }
        }
    }
}