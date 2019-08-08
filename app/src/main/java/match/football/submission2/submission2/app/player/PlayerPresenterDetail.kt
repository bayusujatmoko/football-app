package match.football.submission2.submission2.app.player

import android.util.Log
import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.PlayerResponseDetail
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenterDetail(private val view: PlayerViewDetail,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson
) {

    fun getPlayerDetail(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getPlayer(league.toString())),
                PlayerResponseDetail::class.java
            )
            Log.d("__debug", data.toString())
            uiThread {
                view.showPlayerDetail(data.players)
                view.hideLoading()
            }
        }
    }
}