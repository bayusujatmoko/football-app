package match.football.submission2.submission2.app.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.R.id.add_to_favorite
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.db.Favorite
import match.football.submission2.submission2.app.db.database
import match.football.submission2.submission2.app.model.Event
import match.football.submission2.submission2.app.model.Team
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.Time
import match.football.submission2.submission2.app.util.invisible
import match.football.submission2.submission2.app.util.visible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var idEvent: String
    private lateinit var presenter: DetailPresenter
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private var matchList: MutableList<Event> = mutableListOf()

    private lateinit var progresBar: ProgressBar
    private lateinit var imageViewHomeTeam: ImageView
    private lateinit var imageViewAwayTeam: ImageView
    private lateinit var dateEvents: TextView
    private lateinit var nameHomeTeam: TextView
    private lateinit var nameAwayTeam: TextView
    private lateinit var homeScore: TextView
    private lateinit var awayScore: TextView
    private lateinit var homeGoalDetail: TextView
    private lateinit var awayGoalDetail: TextView
    private lateinit var homeShots: TextView
    private lateinit var awayShots: TextView
    private lateinit var homeLineupGoalkeeper: TextView
    private lateinit var awayLineupGoalkeeper: TextView
    private lateinit var homeLineupDefense: TextView
    private lateinit var awayLineupDefense: TextView
    private lateinit var homeLineupMidfield: TextView
    private lateinit var awayLineupMidfield: TextView
    private lateinit var homeLineupForward: TextView
    private lateinit var awayLineupForward: TextView
    private lateinit var homeLineupSubstitutes: TextView
    private lateinit var awayLineupSubstitutes: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        idEvent = intent.getStringExtra(Constant.DETAIL)
        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailPresenter(this, request, gson)
        presenter.getEvent(idEvent)
        presenter.getBadgeTeam(Constant.ID_HOME_TEAM,Constant.ID_AWAY_TEAM)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progresBar = find(R.id.progress_bar)

        favoriteState()

        swipeRefreshDetail.setOnRefreshListener {
            presenter.getEvent(idEvent)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(${Favorite.ID_EVENT} = {id})",
                    "id" to idEvent)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if(matchList.isNotEmpty()){
                    if (isFavorite) removeFromFavorite() else addtoFavorite()
                    isFavorite = !isFavorite
                    setFavorite()
                }else {
                    Toast.makeText(this,"Belum ada data", Toast.LENGTH_SHORT).show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun addtoFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.ID_EVENT to idEvent,
                    Favorite.DATE_EVENT to matchList[0].dateEvent,
                    Favorite.TIME to matchList[0].strTime,
                    Favorite.HOME_SCORE to matchList[0].intHomeScore,
                    Favorite.AWAY_SCORE to matchList[0].intAwayScore,
                    Favorite.HOME_TEAM to matchList[0].strHomeTeam,
                    Favorite.AWAY_TEAM to matchList[0].strAwayTeam)
            }
            toast("Added to Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(${Favorite.ID_EVENT} = {${Favorite.IDS}})",
                    "${Favorite.IDS}" to idEvent)

            }
            toast("Removed to Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }


    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    private fun substitutes(homeLineupSubs: String? = null, awayLineupSubs: String? = null) {
        homeLineupSubstitutes = find(R.id.home_substitutes)
        awayLineupSubstitutes = find(R.id.away_substitutes)

        homeLineupSubstitutes.text = homeLineupSubs
        awayLineupSubstitutes.text = awayLineupSubs
    }

    private fun forward(homeLineUpForward: String? = null, awaylineUpForward: String? = null) {
        homeLineupForward = find(R.id.home_forward)
        awayLineupForward = find(R.id.away_forward)

        homeLineupForward.text = homeLineUpForward
        awayLineupForward.text = awaylineUpForward
    }

    private fun midfield(HomeMidfield: String? = null, awayMidfield: String? = null) {
        homeLineupMidfield = find(R.id.home_midfield)
        awayLineupMidfield = find(R.id.away_midfield)

        homeLineupMidfield.text = HomeMidfield
        awayLineupMidfield.text = awayMidfield
    }

    private fun defense(homeDefense: String? = null, awayDefense: String? = null) {
        homeLineupDefense = find(R.id.home_defense)
        awayLineupDefense = find(R.id.away_defense)

        homeLineupDefense.text = homeDefense
        awayLineupDefense.text = awayDefense

    }

    private fun goalKeeper(homeGoalKeeper: String? = null, awayGoalKeeper: String? = null) {
        homeLineupGoalkeeper = find(R.id.home_goalkeeper)
        awayLineupGoalkeeper = find(R.id.away_goalkeeper)

        homeLineupGoalkeeper.text = homeGoalKeeper
        awayLineupGoalkeeper.text = awayGoalKeeper
    }

    private fun shoots(homeShot: String? = null, awayShot: String? = null) {
        homeShots = find(R.id.home_shots)
        awayShots = find(R.id.away_shot)

        homeShots.text = homeShot?: ""
        awayShots.text = awayShot?: ""
    }

    private fun goalDetail(homeGoal: String? = null, awayGoal: String? = null) {
        homeGoalDetail = find(R.id.home_goals)
        awayGoalDetail = find(R.id.away_goals)

        homeGoalDetail.text = homeGoal ?: ""
        awayGoalDetail.text = awayGoal ?: ""
    }

    private fun scoreDate(date: String?, time: String?, scoreHome: String? = null, scoreAway: String? = null) {
        dateEvents = find(R.id.match_date)
        homeScore = find(R.id.home_score_match)
        awayScore = find(R.id.away_score_match)

        val date = Time.formatUTCtoGMT(date.toString(), time.toString().substring(0, 5), Constant.DATE)
        dateEvents.text = date
        homeScore.text = scoreHome?: ""
        awayScore.text = scoreAway?: ""

    }

    private fun nameTeam(nameHomeTeam: String? = null, nameAwayTeam: String? = null) {
        this.nameHomeTeam = find(R.id.home_name)
        this.nameAwayTeam = find(R.id.away_name)

        this.nameHomeTeam.text = nameHomeTeam ?: ""
        this.nameAwayTeam.text = nameAwayTeam ?: ""
    }

    override fun showBadgeTeam(dataHome: List<Team>, dataAway: List<Team>) {
        imageViewHomeTeam = find(R.id.img_home)
        imageViewAwayTeam = find(R.id.img_away)
        Picasso.get().load(dataHome[0].strTeamBadge).into(imageViewHomeTeam)
        Picasso.get().load(dataAway[0].strTeamBadge).into(imageViewAwayTeam)
    }

    override fun getEventList(data : List<Event>) {
        swipeRefreshDetail.isRefreshing = false
        matchList.clear()
        matchList.addAll(data)
        scoreDate(data[0].dateEvent, data[0].strTime, data[0].intHomeScore, data[0].intAwayScore)
        nameTeam(data[0].strHomeTeam, data[0].strAwayTeam)
        goalDetail(data[0].strHomeGoalDetails, data[0].strAwayGoalDetails)
        shoots(data[0].intHomeShots, data[0].intAwayShots)
        goalKeeper(data[0].strHomeLineupGoalkeeper, data[0].strAwayLineupGoalkeeper)
        defense(data[0].strHomeLineupDefense, data[0].strAwayLineupDefense)
        midfield(data[0].strHomeLineupMidfield, data[0].strAwayLineupMidfield)
        forward(data[0].strHomeLineupForward, data[0].strAwayLineupForward)
        substitutes(data[0].strHomeLineupSubstitutes, data[0].strAwayLineupSubstitutes)

        presenter.getBadgeTeam(data[0].idHomeTeam.toString(), data[0].idAwayTeam.toString())
    }
}
