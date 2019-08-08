package match.football.submission2.submission2.app.detail

import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {
    @Mock
    private
    lateinit var view: DetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(
            view, apiRepository, gson)
    }

    @Test
    fun getEventTest() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val idEvent = "441613"

        doAsync {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(ApiInterface.getEvent(idEvent)),
                    EventResponse::class.java
                )
            ).thenReturn(response)

            uiThread {
                presenter.getEvent(idEvent)

                Mockito.verify(view).showLoading()
                Mockito.verify(view).getEventList(event)
                Mockito.verify(view).hideLoading()
            }
        }
    }

    @Test
    fun getBadgeTeamTest() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idTeam = "133604"

        doAsync {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(ApiInterface.getTeam(idTeam)),
                    TeamResponse::class.java
                )
            ).thenReturn(response)
            uiThread {
                presenter.getBadgeTeam(idTeam,idTeam)

                Mockito.verify(view).showLoading()
                Mockito.verify(view).showBadgeTeam(teams,teams)
                Mockito.verify(view).hideLoading()
            }
        }
    }
}
