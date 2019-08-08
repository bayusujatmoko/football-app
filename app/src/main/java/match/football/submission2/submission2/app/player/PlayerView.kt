package match.football.submission2.submission2.app.player

import match.football.submission2.submission2.app.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(showPlayerList: List<Player>)
}