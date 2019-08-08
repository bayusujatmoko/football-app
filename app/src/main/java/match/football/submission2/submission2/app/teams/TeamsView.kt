package match.football.submission2.submission2.app.teams

import match.football.submission2.submission2.app.model.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(showTeamList: List<Team>)
}