package match.football.submission2.submission2.app.detail

import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.EventResponse
import match.football.submission2.submission2.app.model.TeamResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson
) {
    fun getEvent(id: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getEvent(id)),
                EventResponse::class.java)
            uiThread {
                view.getEventList(data.events)
                view.hideLoading()
            }

        }
    }

    fun getBadgeTeam(dataHome: String?, dataAway: String?) {
        doAsync {
            val data1 = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getTeam(dataHome.toString())),
                TeamResponse::class.java)
            val data2 = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getTeam(dataAway.toString())),
                TeamResponse::class.java)
            uiThread {
                view.showBadgeTeam(data1.teams, data2.teams)
                view.hideLoading()
            }
        }
    }
}