package match.football.submission2.submission2.app.detail

import match.football.submission2.submission2.app.model.Event
import match.football.submission2.submission2.app.model.Match
import match.football.submission2.submission2.app.model.Team

interface DetailView {
    fun hideLoading()
    fun showLoading()
    fun getEventList(data: List<Event>)
    fun showBadgeTeam(dataHome: List<Team>, dataAway: List<Team>)
}