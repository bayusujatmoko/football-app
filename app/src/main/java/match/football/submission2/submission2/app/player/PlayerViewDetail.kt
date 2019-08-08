package match.football.submission2.submission2.app.player

import match.football.submission2.submission2.app.model.PlayerDetail

interface PlayerViewDetail {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(showPlayerDetail: List<PlayerDetail>)
}