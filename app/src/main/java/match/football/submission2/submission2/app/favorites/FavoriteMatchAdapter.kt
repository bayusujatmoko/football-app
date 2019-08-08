package match.football.submission2.submission2.app.favorites

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import match.football.submission2.submission2.R
import match.football.submission2.submission2.app.db.Favorite
import match.football.submission2.submission2.app.util.Constant
import match.football.submission2.submission2.app.util.Time
import match.football.submission2.submission2.app.util.invisible
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteMatchAdapter(private val favoriteList:List<Favorite>, private val context: Context, val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_list, p0, false))
    }

    override fun getItemCount(): Int = favoriteList.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bindMatch(favoriteList[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val date: TextView = view.find(R.id.dateScheduleTv)
        private val homeName: TextView = view.find(R.id.homeNameTv)
        private val awayName: TextView = view.find(R.id.awayNameTv)
        private val homeScore: TextView = view.find(R.id.homeScoreTv)
        private val awayScore: TextView = view.find(R.id.awayScoreTv)

        fun bindMatch(event: Favorite, listener: (Favorite) -> Unit){
            val dateTime = Time.formatUTCtoGMT(event.dateEvent.toString(), event.strTime.toString().substring(0, 5), Constant.DATE_TIME)
            date.text = dateTime
            homeName.text = event.homeTeam?: ""
            awayName.text = event.awayTeam?: ""
            homeScore.text = event.homeScore?: ""
            awayScore.text = event.awayScore?: ""

            itemView.onClick {
                listener(event)
            }
        }
    }
}