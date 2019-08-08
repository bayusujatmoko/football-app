package match.football.submission2.submission2.app.teams

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.overview.*
import match.football.submission2.submission2.R
import match.football.submission2.submission2.R.id.add_to_favorite
import match.football.submission2.submission2.app.adapter.TeamsPageAdapter
import match.football.submission2.submission2.app.api.ApiRepository
import match.football.submission2.submission2.app.db.Favorite
import match.football.submission2.submission2.app.db.FavoriteTeam
import match.football.submission2.submission2.app.db.database
import match.football.submission2.submission2.app.db.databaseTeams
import match.football.submission2.submission2.app.model.Team
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.invisible
import match.football.submission2.submission2.app.util.visible
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class TeamsDetailActivity : AppCompatActivity(),TeamsView {
    private lateinit var idTeam: String
    private lateinit var presenter: TeamsPresenter
    private val teams: MutableList<Team> = mutableListOf()
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        supportActionBar?.title = "Teams Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        idTeam = intent.getStringExtra(Constant.DETAIL)

        //load data
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        presenter.getTeam(idTeam)

        favoriteState()
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
                if(teams.isNotEmpty()){
                    if (isFavorite) removeFromFavorite() else addToFavorite()
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

    private fun removeFromFavorite() {
        try {
            databaseTeams.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM, "(${FavoriteTeam.TEAM_ID} = {${FavoriteTeam.IDS}})",
                    "${FavoriteTeam.IDS}" to idTeam)

            }
            toast("Removed to Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun addToFavorite() {
        try {
            databaseTeams.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to idTeam,
                    FavoriteTeam.TEAM_NAME to teams[0].strTeam,
                    FavoriteTeam.TEAM_BADGE to teams[0].strTeamBadge)
            }
            toast("Added to Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun favoriteState() {
        databaseTeams.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(${FavoriteTeam.TEAM_ID} = {${FavoriteTeam.IDS}})",
                    "${FavoriteTeam.IDS}" to idTeam)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun hideLoading() {
        progressBarDetailTeam.invisible()
    }

    override fun showLoading() {
        progressBarDetailTeam.visible()
    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        val adapter = TeamsPageAdapter(supportFragmentManager)
        viewpager_main.adapter = adapter
        tabs_main.setupWithViewPager(viewpager_main)
        Picasso.get().load(data[0].strTeamBadge).into(img_club)
        tv_name_club.text = data[0].strTeam
        tv_years_club.text = data[0].intFormedYear.toString()
        tv_stadium_club.text = data[0].strStadium
        overview.text = data[0].strDescriptionEN
    }
}