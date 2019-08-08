package match.football.submission2.submission2.app.teams

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
import match.football.submission2.submission2.app.model.Team
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class TeamsAdapter(private val eventList:List<Team>, private val context: Context, val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.team_list, p0, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bindTeam(eventList[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamBadge: ImageView = view.find(ivTeamLogo)
        private val teamName: TextView = view.find(tvNameTeam)

        fun bindTeam(event: Team, listener: (Team) -> Unit){
            Picasso.get().load(event.strTeamBadge).into(teamBadge)
            teamName.text = event.strTeam

            itemView.onClick {
                listener(event)
            }
        }
    }
}