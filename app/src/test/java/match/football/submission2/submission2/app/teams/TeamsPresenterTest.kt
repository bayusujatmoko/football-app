package match.football.submission2.submission2.app.teams

import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.Team
import match.football.submission2.submission2.app.model.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {
    @Mock
    private
    lateinit var view: TeamsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsPresenter(view, apiRepository, gson)
    }

    @Test
    fun getTeamListTest() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val league = "4328"

        doAsync {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(ApiInterface.getTeam(league)),
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            uiThread {
                presenter.getTeamList(league)
                Mockito.verify(view).showLoading()
                Mockito.verify(view).showTeamList(teams)
                Mockito.verify(view).hideLoading()
            }
        }
    }

    @Test
    fun getTeamTest() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val league = "133604"

        doAsync {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(ApiInterface.getTeam(league)),
                   TeamResponse::class.java
                )
            ).thenReturn(response)

            uiThread {
                presenter.getTeam(league)

                Mockito.verify(view).showLoading()
                Mockito.verify(view).showTeamList(teams)
                Mockito.verify(view).hideLoading()
            }
        }
    }
}