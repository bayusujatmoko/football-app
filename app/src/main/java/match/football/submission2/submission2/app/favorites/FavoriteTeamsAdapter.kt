package match.football.submission2.submission2.app.favorites

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import match.football.submission2.submission2.R
import match.football.submission2.submission2.R.id.ivTeamLogo
import match.football.submission2.submission2.R.id.tvNameTeam
import match.football.submission2.submission2.app.db.FavoriteTeam
import match.football.submission2.submission2.app.util.invisible
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteTeamsAdapter(private val favoriteList:List<FavoriteTeam>, private val context: Context, val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.team_list, p0, false))
    }

    override fun getItemCount(): Int = favoriteList.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bindMatch(favoriteList[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamBadge: ImageView = view.find(ivTeamLogo)
        private val teamName: TextView = view.find(tvNameTeam)

        fun bindMatch(event: FavoriteTeam, listener: (FavoriteTeam) -> Unit){
            Picasso.get().load(event.strTeamBadge).into(teamBadge)
            teamName.text = event.strTeam

            itemView.onClick {
                listener(event)
            }
        }
    }
}