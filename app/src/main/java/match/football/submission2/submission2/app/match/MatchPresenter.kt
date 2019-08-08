package match.football.submission2.submission2.app.match

import android.util.Log
import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.MatchResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson
) {

    fun getLastMatchData(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getLastMatch(league)),
                MatchResponse::class.java
            )
            Log.d("__debug", data.toString())
            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }
    }

    fun getNextMatchData(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getNextMatch(league)),
                MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }
    }
}
