package match.football.submission2.submission2.app.teams

import android.util.Log
import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson) {

    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getTeamList(league)),
                TeamResponse::class.java
            )
            Log.d("__debug", data.toString())
            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }

    fun getTeam(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getTeam(league.toString())),
                TeamResponse::class.java
            )
            Log.d("__debug", data.toString())
            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams)
            }
        }
    }
}