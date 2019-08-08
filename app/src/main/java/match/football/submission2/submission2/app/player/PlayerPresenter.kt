package match.football.submission2.submission2.app.player

import android.util.Log
import com.google.gson.Gson
import match.football.submission2.submission2.app.api.ApiInterface
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.model.PlayerResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenter(private val view: PlayerView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson
) {

    fun getPlayerList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiInterface.getPlayerList(league)),
                PlayerResponse::class.java
            )
            Log.d("__debug", data.toString())
            uiThread {
                view.showPlayerList(data.player)
                view.hideLoading()
            }
        }
    }
}