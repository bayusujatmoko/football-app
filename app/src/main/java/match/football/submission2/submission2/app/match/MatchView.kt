package match.football.submission2.submission2.app.match

import match.football.submission2.submission2.app.model.Match

interface MatchView {
    fun hideLoading()
    fun showLoading()
    fun showMatchList(showMatchList:List<Match>)
}